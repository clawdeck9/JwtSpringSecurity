package com.cluster9.jwtspringsecurity.service;

import com.cluster9.jwtspringsecurity.entities.AppRole;
import com.cluster9.jwtspringsecurity.entities.AppUser;

public interface AccountService {
	// these methods are not called by Spring
	public AppUser saveUser(AppUser user);
	public AppRole saveRole(AppRole role);
	public void addRoleToUser(String user, String role);
	// this method is called by Spring security through the userDetailsService obj
	public AppUser findUserByUsername(String username);
}
