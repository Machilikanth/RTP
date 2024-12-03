package com.toucan.tourtptrs.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.toucan.tourtptrs.constants.EnumConstants.EntityTypeEnum;
import com.toucan.tourtptrs.constants.EnumConstants.vpaStatusEnum;
import com.toucan.tourtptrs.dto.AccountDetailsDto;
import com.toucan.tourtptrs.helper.LinkBankAccount;
import com.toucan.tourtptrs.helper.UPIID;
import com.toucan.tourtptrs.helper.UserBankDetails;
import com.toucan.tourtptrs.models.AccountDetails;
import com.toucan.tourtptrs.models.BankDetails;
import com.toucan.tourtptrs.models.User;
import com.toucan.tourtptrs.repositories.AccountDetailsRepository;
import com.toucan.tourtptrs.repositories.BankDetailsRepository;
import com.toucan.tourtptrs.repositories.UserRepository;

@Service
public class AccountDetailsService extends AbstractCoreService<AccountDetails>{
	private AtomicInteger counter = new AtomicInteger(2);
	@Autowired
	private BankDetailsRepository bankDetailsRepository;
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AccountDetailsRepository accountDetailsRepository;
	
	@Override
	public Class<AccountDetails> getEntityClass() {
		return AccountDetails.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getEntityDTO() {
		return (T) new AccountDetailsDto();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getEntityObject() {
		return (T) new AccountDetails();
	}

	@Override
	public EntityTypeEnum getEntityTypeEnum() {
		return EntityTypeEnum.ACCOUNTDETAILS;
	}

	public LinkBankAccount linkAcc(LinkBankAccount entity) {
	    List<BankDetails> banksListByMobileNum = bankDetailsRepository.findByMobileNumber(entity.getMobileNumber());
	    if (banksListByMobileNum.isEmpty()) {
	        throw new RuntimeException("No Account is found with this Mobile Number");
	    }
	    BankDetails bankDetails = banksListByMobileNum.stream()
	            .filter(details -> entity.getBankName().equals(details.getBankName()))
	            .findFirst()
	            .orElse(null);
 
	    if (bankDetails == null) {
	        throw new RuntimeException("No matching account found for the given mobile number and bank name");
	    }
	    Query query1 = new Query(Criteria.where("userBankDetails.mobileNumber").is(entity.getMobileNumber()));
	    AccountDetails accountDetails = mongoTemplate.findOne(query1, AccountDetails.class);
	    if (accountDetails == null) {
	        accountDetails = new AccountDetails();
	        accountDetails.setUserBankDetails(new ArrayList<>());
	    }
	    ArrayList<UserBankDetails> userBankDetails = accountDetails.getUserBankDetails();
	    Optional<UserBankDetails> existingBankDetail = userBankDetails.stream()
	            .filter(details -> details.getBankName().equals(entity.getBankName()) && details.getMobileNumber().equals(entity.getMobileNumber()))
	            .findFirst();
 
	    if (existingBankDetail.isPresent()) {
	        throw new RuntimeException("Bank Account is already linked");
	    }
 
	    UserBankDetails userbankdetails = new UserBankDetails();
	    userbankdetails.setAccountNumber(bankDetails.getAccountNumber());
	    userbankdetails.setAccountHolderName(bankDetails.getAccountHolderName());
	    userbankdetails.setAccountType(bankDetails.getAccountType());
	    userbankdetails.setBankBalance(bankDetails.getBankBalance());
	    userbankdetails.setBankName(bankDetails.getBankName());
	    userbankdetails.setBranchName(bankDetails.getBranchName());
	    userbankdetails.setIfscCode(bankDetails.getIfscCode());
	    userbankdetails.setIsPopular(bankDetails.getIsPopular());
	    userbankdetails.setMobileNumber(bankDetails.getMobileNumber());
 
	    final String vpa = createVPA(userbankdetails, userbankdetails.getMobileNumber(), userbankdetails.getBankName());
	    userbankdetails.setVpas(new ArrayList<>());
	    userbankdetails.getVpas().add(new UPIID(vpa,vpaStatusEnum.ACTIVATE));
	    Optional<User> userOptional = userRepository.findById(entity.getUserId());
 
	    if (userOptional.isPresent()) {
	        User user = userOptional.get();
	        if (!entity.getMobileNumber().equals(user.getMobileNumber())) {
	            throw new IllegalArgumentException("User ID and Mobile Number do not match.");
	        }
	        String userAccId = user.getAccId();
	        if (userAccId == null) {
	            accountDetails = mongoTemplate.insert(accountDetails);
	            user.setAccId(accountDetails.getId());
	            if (user.getMobileNumber().equals(userbankdetails.getMobileNumber())) {
	            	  if (user.getVpas() == null) {
		                    user.setVpas(new ArrayList<>());
		                }
		                user.getVpas().add(new UPIID(vpa,vpaStatusEnum.ACTIVATE));
		                mongoTemplate.save(user);
	 
		            }
	 
		            user = mongoTemplate.save(user);
		        } else {
	            Query query = new Query(Criteria.where("_id").is(user.getAccId()));
	            Update update = new Update().push("userBankDetails", userbankdetails);
	            mongoTemplate.updateFirst(query, update, AccountDetails.class);
	            ArrayList<UPIID> vpalist=user.getVpas();
	            if (vpalist == null) {
	            	vpalist = new ArrayList<>();
	            }
	            boolean vpaExists = vpalist.stream().anyMatch(vpas -> vpas.getVpa().equals(vpa));
	            if (!vpaExists) {
	                user.getVpas().add(new UPIID(vpa, vpaStatusEnum.ACTIVATE));
	                mongoTemplate.save(user);
	            }
	        }
 
	    }
	    else {
	    	throw new IllegalArgumentException("user doesnot exist with userid");
	    }
	    accountDetails.getUserBankDetails().add(userbankdetails);
	    mongoTemplate.save(accountDetails);
 	    return entity;
	}
	
	public String createVPA(UserBankDetails user, String mobileNumber, String bankName) {
	    if (mobileNumber == null || mobileNumber.isEmpty()) {
	        throw new IllegalArgumentException("Please provide a mobile number");
	    }
	    if (bankName == null || bankName.isEmpty()) {
	        throw new IllegalArgumentException("Please provide a bank name");
	    }
	    if (user == null) {
	        throw new IllegalArgumentException("User cannot be null");
	    }
 
	    String identifier = user.getMobileNumber();
	    System.out.println("Phone number: " + identifier);
 
	    Optional<User> userOptional = userRepository.findByMobileNumber(mobileNumber);
	    if (!userOptional.isPresent()) {
	        throw new IllegalArgumentException("User with mobile number " + mobileNumber + " not found");
	    }
	    User foundUser = userOptional.get();
 
	    String vpa = foundUser.getMobileNumber() + "@toucan";
	    System.out.println("Initial VPA: " + vpa);
 
	    while (vpaExists(vpa)) {
	        vpa = identifier + "-" + generateNumber() + "@toucan";
	        System.out.println("New VPA: " + vpa);
	    }
 
	    return vpa;
	}
 
	private boolean vpaExists(String vpa) {
	    Query query = new Query(Criteria.where("vpa.vpa").is(vpa));
	    List<User> users = mongoTemplate.find(query, User.class);
	    return !users.isEmpty();
	}
 
	private String generateNumber() {
	    return String.valueOf(counter.getAndIncrement());
	}
	public UserBankDetails customizeVpa(String accountId, String vpa, String mobilenumber, final String newCustomVpa) {
	    List<AccountDetails> optionalAccountDetails = accountDetailsRepository.findAll();
 
	    if (optionalAccountDetails.isEmpty()) {
	        throw new IllegalArgumentException("AccountDetails not found for id: " + accountId);
	    }
 
	    List<String> vpasMap = getAllExistingVpas();
	   
 
	    for (AccountDetails accountDetails : optionalAccountDetails) {
	        List<UserBankDetails> userBankDetailsList = accountDetails.getUserBankDetails();
 
	        for (UserBankDetails details : userBankDetailsList) {
	        	ArrayList<UPIID> upiidlist=details.getVpas();
	        	for(UPIID upiid:upiidlist) {
	        	
	        	if(vpa.equals(upiid.getVpa()) &&mobilenumber.equals(details.getMobileNumber())) {
                    String customVpa = upiid.getVpa();
                    System.out.println("Found UserBankDetails:");
	            System.out.println("VPA: " + details.getVpas());
	            System.out.println("Mobile Number: " + details.getMobileNumber());
	            System.out.println("CustomizeVpa: " + customVpa);
	            long existingVpaCount = upiidlist.stream()
                        .filter(upi -> upi.getVpa() != null)
                        .count();

                    if (existingVpaCount >= 2) {
                        throw new IllegalArgumentException("Not Available");
                    }
	            if (isValidCustomVpa(newCustomVpa)) {
                    String newCustomVpaBase = newCustomVpa.contains("@") ? newCustomVpa.split("@")[0] : newCustomVpa;
                    String base = "@toucan";
                    String newVpa = newCustomVpaBase + base;
                    List<String> allExistingVpas = new ArrayList<>(vpasMap);
                    allExistingVpas.addAll(vpasMap);

                    boolean vpaExists = allExistingVpas.stream()
                            .anyMatch(existingVpa -> existingVpa.equals(newVpa));

                    if (vpaExists) {
                        throw new IllegalArgumentException("Not Available");
                    }
                    UPIID newUpiId = new UPIID(newVpa, vpaStatusEnum.DEACTIVATE);
                    upiidlist.add(newUpiId);
                    Optional<User> userOptional = userRepository.findByMobileNumber(details.getMobileNumber());
	                
	                if (userOptional.isPresent()) {
	                    User user = userOptional.get();
	                    if (user.getMobileNumber().equals(details.getMobileNumber())) {
	                        if (user.getVpas() == null) {
	                            user.setVpas(new ArrayList<>());
	                        }
	                        user.getVpas().add(newUpiId);
                            mongoTemplate.save(user);
	                    }
	                }
	                
	                accountDetailsRepository.save(accountDetails);
	                return details;
	            } else {
	                throw new IllegalArgumentException("Invalid custom VPA: " + newCustomVpa);
	            }
	        }
	    }
	    }
	    }
	    throw new IllegalArgumentException("UserBankDetails not found for vpa: " + vpa + " and mobileNumber: " + mobilenumber);
	}
 
	private boolean isValidCustomVpa(String customVpa) {
	    if (customVpa == null || customVpa.trim().isEmpty()) {
	        return false;
	    }
	    if (!customVpa.matches("^[a-zA-Z0-9.-]*$")) {
	        throw new IllegalArgumentException("Not Available");
	    }
	   
	    return customVpa.length() <= 40;
	}
	public List<String> createVPAsuggestions(User user) {
	    List<String> suggestions = new ArrayList<>();
	    List<UPIID> vpas = user.getVpas() != null ? user.getVpas() : new ArrayList<>();
List<AccountDetails> accountdetails=accountDetailsRepository.findAll();
System.out.println("details :"+accountdetails);
 List<String> existingVpas = getAllExistingVpas();
	    for (UPIID vpadata : vpas) {
	    	String vpaa=vpadata.getVpa();
	        Optional<User> optionalUser = userRepository.findByVpa(vpaa);
	        Optional<AccountDetails> accountuser=accountDetailsRepository.findByVpa(vpaa);
 
	        if (!optionalUser.isPresent()) {
	            throw new IllegalArgumentException("VPA does not exist: " + vpaa);
	        }
 
	        AccountDetails users = accountuser.get();
	        User userdata = optionalUser.orElseThrow(() -> new IllegalArgumentException("User does not exist for VPA: " + vpaa));
	        String base = "@toucan";
	        List<UserBankDetails> bankDetails = users.getUserBankDetails();
	        for (UserBankDetails details : bankDetails) {
	            String existingVpased = vpadata.getVpa();
	            addVpaSuggestions(userdata, existingVpased, suggestions, base, existingVpas);
	        }
	    }
	    List<String> uniqueSuggestions = suggestions.stream().distinct().collect(Collectors.toList());
	    System.out.println("Generated suggestions: " + uniqueSuggestions);
	    return uniqueSuggestions;
	}
 
	private void addVpaSuggestions(User userdata, String existingVpased, 
			List<String> suggestions, String base, List<String> existingVpas) {
	    if (userdata.getFirstName() != null && userdata.getLastName() != null) {
	        String nameVpa = (userdata.getFirstName().concat(userdata.getLastName()) + base).toLowerCase();
	        if (!vpaExists(nameVpa, existingVpas)) {
	            suggestions.add(nameVpa);
	            System.out.println("Added name VPA suggestion: " + nameVpa);
	        } else {
	            nameVpa = generateUniqueVpa(userdata.getFirstName().concat(userdata.getLastName()) + base, existingVpas);
	            suggestions.add(nameVpa);
	            System.out.println("Added alternative name VPA suggestion: " + nameVpa);
	        }
	    }
 
	    if (userdata.getMobileNumber() != null && !userdata.getMobileNumber().isEmpty()) {
	        String mobileVpa = (userdata.getMobileNumber() + base).toLowerCase();
	        if (!vpaExists(mobileVpa, existingVpas)) {
	            suggestions.add(mobileVpa);
	            System.out.println("Added mobile number VPA suggestion: " + mobileVpa);
	        } else {
	            mobileVpa = generateUniqueVpa(userdata.getMobileNumber() + base, existingVpas);
	            suggestions.add(mobileVpa);
	            System.out.println("Added alternative mobile number VPA suggestion: " + mobileVpa);
	        }
	    }
 
	    if (userdata.getEmailId() != null && !userdata.getEmailId().isEmpty()) {
	        String emailPrefix = userdata.getEmailId().split("@")[0];
	        String emailVpa = (emailPrefix + base).toLowerCase();
	        if (!vpaExists(emailVpa, existingVpas)) {
	            suggestions.add(emailVpa);
	            System.out.println("Added email VPA suggestion: " + emailVpa);
	        } else {
	            emailVpa = generateUniqueVpa(emailPrefix + base, existingVpas);
	            suggestions.add(emailVpa);
	            System.out.println("Added alternative email VPA suggestion: " + emailVpa);
	        }
	    }
	}
 
	private boolean vpaExists(String vpa, List<String> existingVpas) {
	    return existingVpas.contains(vpa);
	}
 
	private String generateUniqueVpa(String baseVpa, List<String> existingVpas) {
	    int counter = 2;
	    String newVpa = baseVpa;
	    while (vpaExists(newVpa, existingVpas)) {
	        newVpa = baseVpa.split("@")[0] + "-" + counter + "@toucan";
	        counter++;
	    }
	    return newVpa.toLowerCase();
	}
	
	private List<String> getAllExistingVpas() {
	    List<AccountDetails> allAccountDetails = accountDetailsRepository.findAll();
	    return allAccountDetails.stream()
	            .flatMap(accountDetails -> accountDetails.getUserBankDetails().stream())
	            .flatMap(userBankDetails -> userBankDetails.getVpas().stream())
	            .map(UPIID::getVpa)
	            .collect(Collectors.toList());
	}
}
