//package com.toucan.rtp.core.service;
//
//import java.util.Collection;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import com.toucan.rtp.core.model.User;
//
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//
//
//@NoArgsConstructor
//@AllArgsConstructor
//@Configuration
//public class CustomUserDetails implements UserDetails {
//
//	private static final long serialVersionUID = -4630514114427391542L;
//	private String firstName;
//	private String mobileNumber;
//	private String emailId;
//
//	public CustomUserDetails(User user) {
//		
//		this.firstName = user.getFirstName();
//		this.mobileNumber = user.getMobileNumber();
//		this.emailId = user.getEmailId();
//	}
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return null; // Implement authorities if needed
//	}
//
//	@Override
//	public String getPassword() {
//		return null; // No password required
//	}
//
//	@Override
//	public String getUsername() {
//		return firstName;
//	}
//
//	public String getMobileNumber() {
//		return mobileNumber;
//	}
//
//	public String getEmail() {
//		return emailId;
//	}
//
//	@Override
//	public boolean isAccountNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		return true;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		return true;
//	}
//}
