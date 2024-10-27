package com.myStartup.config;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.myStartup.security.ShopUserDetails;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

	private String secretKey;
	private int expirationTime;

	public String generateTokenForUser(Authentication authentication) {
		// get the login user
		ShopUserDetails loggedInUser = (ShopUserDetails) authentication.getPrincipal();

		// get all the roles of a user
		List<String> roles = loggedInUser.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		return Jwts.builder().setSubject(loggedInUser.getEmail()).claim("id", loggedInUser.getId())
				.claim("roles", roles).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + expirationTime))
				.signWith(key(), SignatureAlgorithm.HS256).compact();
	}

	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	}

	public String getUsernameForToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
			.setSigningKey(key())
			.build()
			.parseClaimsJws(token);
			return true;
		}catch(JwtException | IllegalArgumentException e) {
			throw new JwtException(e.getMessage());
			
		}
	}
}
