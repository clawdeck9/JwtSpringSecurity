package com.cluster9.jwtspringsecurity.service;

import com.cluster9.jwtspringsecurity.entities.AppRole;
import com.cluster9.jwtspringsecurity.entities.AppUser;

public interface AccountService {
	public AppUser saveUser(AppUser user);
	public AppRole saveRole(AppRole role);
	public void addRoleToUser(String user, String role);
	public AppUser findUserByUsername(String username);
}
