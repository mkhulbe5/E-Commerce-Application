package com.myStartup.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.myStartup.security.ShopUserDetailsService;
import com.myStartup.security.UserDetails;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthTokenFilter extends OncePerRequestFilter {

	private JwtUtils jwtUtils;
	private ShopUserDetailsService shopUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = parseJwst(request);
			if (StringUtils.hasText(jwt) && jwtUtils.validateToken(jwt)) {
				String username = jwtUtils.getUsernameForToken(jwt);
				UserDetails loadByUserName = shopUserDetailsService.loadByUserName(username);
				var auth = new UsernamePasswordAuthenticationToken(loadByUserName, loadByUserName.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} catch (JwtException e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write(e.getMessage() + "Invalid or expired token" + " "+ "login again");
			return;
		}
		catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write(e.getMessage());
			return;
		}
		filterChain.doFilter(request, response);
	}

	public String parseJwst(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
			return header.substring(7);
		}
		return null;
	}

}
