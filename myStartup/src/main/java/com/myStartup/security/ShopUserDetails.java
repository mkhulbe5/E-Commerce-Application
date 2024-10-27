package com.myStartup.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.myStartup.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopUserDetails implements UserDetails {

	private Long id;
	private String password;
	private String email;
	private Collection<GrantedAuthority> authorities;

	public static ShopUserDetails builUserDetails(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		return new ShopUserDetails(user.getId(), user.getPassword(), user.getEmail(), authorities);
	}

	@Override
	public boolean isAccountExpired() {
		return UserDetails.super.isAccountExpired();
	}

	@Override
	public boolean isAccountUnlocked() {
		return UserDetails.super.isAccountUnlocked();
	}

	@Override
	public boolean isEnabled() {
		return UserDetails.super.isEnabled();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return UserDetails.super.isCredentialsNonExpired();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUserName() {
		return email;
	}

}
