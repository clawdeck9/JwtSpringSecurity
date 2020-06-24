package com.cluster9.jwtspringsecurity.web;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cluster9.jwtspringsecurity.entities.AppUser;
import com.cluster9.jwtspringsecurity.service.AccountService;
@RestController
public class AccountRestController {
	@Autowired
	private AccountService ac;
	@PostMapping("/register")
	public AppUser register(@RequestBody RegisterForm  userForm){
		if(ac.findUserByUsername(userForm.getUsername()) != null) 
			throw new RuntimeException("The user already exists");
		if(!(userForm.getPassword().equals(userForm.getRepassword())))
			throw new RuntimeException("the passwords are different");
		AppUser user = new AppUser();
		user.setUsername(userForm.getUsername());
		user.setPassword(userForm.getPassword());
		ac.saveUser(user);
		ac.addRoleToUser(user.getUsername(), "USER");
		return user;
	}
}
