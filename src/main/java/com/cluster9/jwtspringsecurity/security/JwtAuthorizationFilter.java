package com.cluster9.jwtspringsecurity.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

// this filter creates a AuthenticationToken from the jwt and adds the roles to it and 
// sends it to the SecurityContext,the jwt is received directly from the client

public class JwtAuthorizationFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = req.getHeader(SecurityConstants.HEADER_STRING);
		// if there is no Jwt, stop here
		if (jwt == null) {
			filterChain.doFilter(req, resp);
			return; 
		}
		// if the jwt does not start with the bearer constant, stop here
		if(! jwt.startsWith(SecurityConstants.TOKEN_PREFIX)){
			filterChain.doFilter(req, resp);
			return;
		}
		Claims claims = Jwts.parser()
				.setSigningKey(SecurityConstants.SECRET)
				.parseClaimsJws(jwt.replace(SecurityConstants.TOKEN_PREFIX, ""))// suppress the prefix in the token string
				.getBody();
		System.out.println("claims = " + claims);
		String username = claims.getSubject();
		ArrayList<Map<String, String>> roles = (ArrayList<Map<String,String>>) claims.get("roles");
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		roles.forEach(r -> authorities.add(new SimpleGrantedAuthority(r.get("authority"))));
		
		UsernamePasswordAuthenticationToken authenticatedUserToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
		SecurityContextHolder.getContext().setAuthentication(authenticatedUserToken);
		filterChain.doFilter(req, resp);
		
	}
}
