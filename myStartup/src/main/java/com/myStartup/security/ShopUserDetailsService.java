package com.myStartup.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myStartup.model.User;
import com.myStartup.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopUserDetailsService implements UserDetailService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadByUserName(String email) throws UsernameNotFoundException {
		User user = Optional.ofNullable(userRepository.findByEmail(email)).orElseThrow(()->new UsernameNotFoundException("User Not Found"));
		return ShopUserDetails.builUserDetails(user);
	}

}
