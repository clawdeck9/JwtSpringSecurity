package com.cluster9.jwtspringsecurity.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cluster9.jwtspringsecurity.entities.AppUser;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	AuthenticationManager am;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		// we'll create a client that sends the name as a json stream
		// String username = request.getParameter("username"); // use this if format is different
		AppUser appUser = null;
		try { // we should create a mapper as a bean and create a method that supply the bean to Spring
			appUser = new ObjectMapper().readValue(request.getInputStream(), AppUser.class );
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		System.out.println("username in Auth " + appUser.getUsername());
		return am.authenticate(new UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword()));
	}

	public JwtAuthenticationFilter(AuthenticationManager am) {
		super();
		this.am = am;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, 
			HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		User sprUser = (User) authResult.getPrincipal();
		String jwt = Jwts.builder()
				.setSubject(sprUser.getUsername())
				.setExpiration(new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS256, SecurityConstants.SECRET)
				.claim("roles", sprUser.getAuthorities())
				.compact();
		response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + jwt);
			
	}

}
