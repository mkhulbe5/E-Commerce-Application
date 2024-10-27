package com.myStartup.security;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailService {
	public UserDetails loadByUserName(String username) throws UsernameNotFoundException;
}
