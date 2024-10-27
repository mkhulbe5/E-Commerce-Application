package com.myStartup.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public interface UserDetails {
	Collection<? extends GrantedAuthority> getAuthorities();

	String getPassword();

	String getUserName();

	default boolean isAccountExpired() {
		return true;
	}

	default boolean isAccountUnlocked() {
		return true;
	}

	default boolean isEnabled() {
		return true;
	}

	default boolean isCredentialsNonExpired() {
		return true;
	}
}
