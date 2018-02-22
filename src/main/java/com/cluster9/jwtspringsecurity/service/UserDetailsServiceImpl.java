package com.cluster9.jwtspringsecurity.service;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cluster9.jwtspringsecurity.entities.AppUser;

@Service // no annots in the interface declaration
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private AccountService accountService;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = accountService.findUserByUsername(username);
		if (user == null) throw new UsernameNotFoundException("username not found");
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(u -> authorities.add(new SimpleGrantedAuthority(u.getRoleName())));
		return new User(user.getUsername(), user.getPassword(),authorities);
	}
}
