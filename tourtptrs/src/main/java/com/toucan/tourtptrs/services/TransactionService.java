package com.toucan.tourtptrs.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.toucan.tourtptrs.constants.EnumConstants.TransactionStatus;
import com.toucan.tourtptrs.helper.Transactions;
import com.toucan.tourtptrs.helper.UserBankDetails;
import com.toucan.tourtptrs.models.AccountDetails;
import com.toucan.tourtptrs.models.UpiPin;
import com.toucan.tourtptrs.models.User;
import com.toucan.tourtptrs.models.Wallet;
import com.toucan.tourtptrs.models.requests.TransactionWalletRequest;
import com.toucan.tourtptrs.models.requests.VpaTransactionRequest;
import com.toucan.tourtptrs.models.response.TransactionResponse;
import com.toucan.tourtptrs.models.response.VpaTransactionResponseHelper;
import com.toucan.tourtptrs.repositories.AccountDetailsRepository;
import com.toucan.tourtptrs.repositories.TransactionRepository;
import com.toucan.tourtptrs.repositories.UpiPinRepository;
import com.toucan.tourtptrs.repositories.UserRepository;
import com.toucan.tourtptrs.repositories.WalletRepository;

@Service
public class TransactionService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	WalletRepository walletRepo;

	@Autowired
	AccountDetailsRepository accountRepo;

	@Autowired
	TransactionRepository transactionRepo;

	@Autowired
	UpiPinRepository upiPinRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	public TransactionResponse<Wallet> postWalletTransaction(TransactionWalletRequest requestObject) {
		TransactionResponse<Wallet> transactionResponse = new TransactionResponse<Wallet>();

		Optional<User> currentUser = userRepo.findById(requestObject.getUserId());
		UpiPin pin = upiPinRepository.findByUpiPin(requestObject.getPin());

		if (currentUser.isPresent()) {
			Optional<AccountDetails> userBankAccounts = accountRepo.findById(requestObject.getAccountsId());

			if (userBankAccounts.isPresent()) {
				AccountDetails accountDetails = userBankAccounts.get();

				ArrayList<UserBankDetails> account = accountDetails.getUserBankDetails();

				for (int i = 0; i < account.size(); i++) {
					if (account.get(i).getAccountNumber().equals(requestObject.getAccountNumber())) {
						Optional<Wallet> getWallet = walletRepo.findById(requestObject.getUserId());

						if (getWallet.isPresent()) {

							Wallet userWallet = getWallet.get();
							if (account.get(i).getBankBalance().getValue().compareTo(requestObject.getAmount()) > 0) {
								if (requestObject.getPin().equals(pin.getUpiPin())) {
									userWallet.setWalletBalance(
											userWallet.getWalletBalance().add(requestObject.getAmount()));

									account.get(i).getBankBalance().setValue(account.get(i).getBankBalance().getValue()
											.subtract(requestObject.getAmount()));
									walletRepo.save(userWallet);

									accountRepo.save(accountDetails);

									transactionResponse.setStatusCode(HttpStatus.OK.value());
									transactionResponse.setSuccess(true);
									transactionResponse.setMessage("Transaction successful");
									transactionResponse.setResult(userWallet);
									return transactionResponse;

								} else {
									transactionResponse.setStatusCode(HttpStatus.OK.value());
									transactionResponse.setSuccess(false);
									transactionResponse.setMessage("Pin mismatch");

									return transactionResponse;
								}
							} else if (userWallet.getWalletBalance().compareTo(requestObject.getAmount()) > 0) {
								transactionResponse.setStatusCode(HttpStatus.OK.value());
								transactionResponse.setSuccess(false);
								transactionResponse.setMessage("Low balance");

								return transactionResponse;
							}

						} else {
							transactionResponse.setStatusCode(HttpStatus.OK.value());
							transactionResponse.setSuccess(false);
							transactionResponse.setMessage("Kyc verification pending");
							return transactionResponse;
						}
					} else {
						transactionResponse.setStatusCode(HttpStatus.OK.value());
						transactionResponse.setSuccess(false);
						transactionResponse.setMessage("account doesn't exsist");
					}
				}
			}

		} else {
			transactionResponse.setStatusCode(HttpStatus.OK.value());
			transactionResponse.setSuccess(false);
			transactionResponse.setMessage("user doesn't exsist");
		}

		transactionRepo.save(transactionResponse);
		return transactionResponse;
	}

	public TransactionResponse<VpaTransactionResponseHelper> postVpaTransaction(VpaTransactionRequest requestObject) {
		TransactionResponse<VpaTransactionResponseHelper> transactionResponse = new TransactionResponse<VpaTransactionResponseHelper>();
		AccountDetails debitaccount = accountRepo.findbyvpa(requestObject.getDebitAccountVpaId());
		System.out.println("debit" + debitaccount);
		AccountDetails creditaccount = accountRepo.findbyvpa(requestObject.getCreditAccountVpaId());
		if (debitaccount != null && creditaccount != null) {
			UpiPin pin = upiPinRepository.findByAccNum(debitaccount.getUserBankDetails().get(0).getAccountNumber());
			if (requestObject.getPin().equals(pin.getUpiPin())) {
				if (debitaccount.getUserBankDetails().get(0).getBankBalance().getValue()
						.compareTo(requestObject.getTransaction().get(0).getAmount()) < 0) {
					transactionResponse = setTransactionResponse(HttpStatus.OK.value(), false, "insufficient funds",
							requestObject.getCreditAccountVpaId(), requestObject.getDebitAccountVpaId(),
							TransactionStatus.FAILURE);
					transactionRepo.save(transactionResponse);
					return transactionResponse;
				}
				debitaccount.getUserBankDetails().get(0).getBankBalance()
						.setValue(debitaccount.getUserBankDetails().get(0).getBankBalance().getValue()
								.subtract(requestObject.getTransaction().get(0).getAmount()));
				creditaccount.getUserBankDetails().get(0).getBankBalance().setValue(creditaccount.getUserBankDetails()
						.get(0).getBankBalance().getValue().add(requestObject.getTransaction().get(0).getAmount()));

				Transactions creditTransaction = new Transactions("Credit to " + requestObject.getCreditAccountVpaId(),
						"CREDIT", LocalDateTime.now(), requestObject.getTransaction().get(0).getAmount());
				Transactions debitTransaction = new Transactions("Debit from " + requestObject.getDebitAccountVpaId(),
						"DEBIT", LocalDateTime.now(), requestObject.getTransaction().get(0).getAmount());

				if (debitaccount.getUserBankDetails().get(0).getTransaction() == null) {
					debitaccount.getUserBankDetails().get(0).setTransaction(new ArrayList<>());
				}
				debitaccount.getUserBankDetails().get(0).getTransaction().add(debitTransaction);

				if (creditaccount.getUserBankDetails().get(0).getTransaction() == null) {
					creditaccount.getUserBankDetails().get(0).setTransaction(new ArrayList<>());
				}
				creditaccount.getUserBankDetails().get(0).getTransaction().add(creditTransaction);
				accountRepo.save(debitaccount);
				accountRepo.save(creditaccount);
				transactionResponse = setTransactionResponse(200, true, "Transaction Successful",
						requestObject.getCreditAccountVpaId(), requestObject.getDebitAccountVpaId(),
						TransactionStatus.SUCCESS);
				transactionRepo.save(transactionResponse);
				return transactionResponse;
			} else {
				transactionResponse = setTransactionResponse(400, false, "pin mismatch",
						requestObject.getCreditAccountVpaId(), requestObject.getDebitAccountVpaId(),
						TransactionStatus.FAILURE);
			}
		} else {
			transactionResponse = setTransactionResponse(400, false, "vpa does not exixts",
					requestObject.getCreditAccountVpaId(), requestObject.getDebitAccountVpaId(),
					TransactionStatus.FAILURE);
		}
		return transactionResponse;
	}

	TransactionResponse<VpaTransactionResponseHelper> setTransactionResponse(int statusCode, Boolean success,
			String message, String creditVpaId, String debitVpaId, TransactionStatus transactionStatus) {
		TransactionResponse<VpaTransactionResponseHelper> transactionResponse = new TransactionResponse<VpaTransactionResponseHelper>();

		VpaTransactionResponseHelper responseHelper = new VpaTransactionResponseHelper();

		responseHelper.setId(UUID.randomUUID().toString());
		responseHelper.setCreditVpaId(creditVpaId);
		responseHelper.setDebitVpaId(debitVpaId);
		responseHelper.setStatus(transactionStatus);
		responseHelper.setCreateAt(LocalDateTime.now());

		transactionResponse.setStatusCode(statusCode);
		transactionResponse.setMessage(message);
		transactionResponse.setSuccess(success);
		transactionResponse.setResult(responseHelper);

		return transactionResponse;

	}


	public List<AccountDetails> credit(@RequestBody AccountDetails req) {
		Query query = new Query();
		query.addCriteria(
				Criteria.where("userBankDetails").elemMatch(Criteria.where("mobileNumber").is(req.getUserBankDetails().get(0).getMobileNumber())
						.and("transaction").elemMatch(Criteria.where("transactiontype").in("CREDIT", "DEBIT"))));

		List<AccountDetails> list = mongoTemplate.find(query, AccountDetails.class);
		return list;
	}
	
	public List<AccountDetails> mobilenumber(@RequestBody AccountDetails req){
		List<AccountDetails> details=accountRepo.findbymobilenumber(req.getUserBankDetails().get(0).getMobileNumber());
		return details;
		
	}
}
