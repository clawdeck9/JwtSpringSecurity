package com.cluster9.jwtspringsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cluster9.jwtspringsecurity.dao.AppRoleRepo;
import com.cluster9.jwtspringsecurity.dao.AppUserRepo;
import com.cluster9.jwtspringsecurity.entities.AppRole;
import com.cluster9.jwtspringsecurity.entities.AppUser;
@Service
@Transactional
public class AccountServiceImpl implements AccountService {
	@Autowired
	private BCryptPasswordEncoder bcEncoder;
	@Autowired
	private AppUserRepo userRepo;
	@Autowired 
	private AppRoleRepo roleRepo;
	@Override
	public AppUser saveUser(AppUser user) {
		String hashPw = bcEncoder.encode(user.getPassword());
		user.setPassword(hashPw);
		return userRepo.save(user);
	}

	@Override
	public AppRole saveRole(AppRole role) {
		return roleRepo.save(role);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		AppUser user=userRepo.findByUsername(username);
		AppRole role=roleRepo.findByRoleName(roleName);
		user.getRoles().add(role);
	}

	@Override
	public AppUser findUserByUsername(String username) {
		return userRepo.findByUsername(username);
	}

}
