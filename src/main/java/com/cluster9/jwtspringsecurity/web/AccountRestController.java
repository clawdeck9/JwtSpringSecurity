package com.cluster9.jwtspringsecurity.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cluster9.jwtspringsecurity.entities.AppUser;
import com.cluster9.jwtspringsecurity.service.AccountService;
@RestController
public class AccountRestController {
	@Autowired
	private AccountService ac;
	@RequestMapping("/register")
	public AppUser register(@RequestBody AppUser user){
		return ac.saveUser(user);
	}
}
