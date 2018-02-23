package com.cluster9.jwtspringsecurity;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cluster9.jwtspringsecurity.dao.TaskRepo;
import com.cluster9.jwtspringsecurity.entities.AppRole;
import com.cluster9.jwtspringsecurity.entities.AppUser;
import com.cluster9.jwtspringsecurity.entities.Task;
import com.cluster9.jwtspringsecurity.service.AccountService;

@SpringBootApplication
public class JwtSpringSecurityApplication implements CommandLineRunner{
	@Autowired
	TaskRepo tr;
	@Autowired
	AccountService accountService;
	

	public static void main(String[] args) {
		SpringApplication.run(JwtSpringSecurityApplication.class, args);
	}
	
	@Bean
	public BCryptPasswordEncoder getBCEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Override
	public void run(String... arg0) throws Exception {
		// creates some user with their roles
		accountService.saveRole(new AppRole(null, "ADMIN"));
		accountService.saveRole(new AppRole(null, "USER"));
		accountService.saveUser(new AppUser(null, "admin", "admin", null));
		accountService.addRoleToUser("admin", "ADMIN");
		accountService.addRoleToUser("admin", "USER");
		accountService.saveUser(new AppUser(null, "user", "user", null));
		accountService.addRoleToUser("user", "USER");
		Stream.of("t1","t2","t3").forEach(t -> tr.save(new Task(null, t)));
		tr.findAll().forEach(t -> System.out.println("name: " + t.getTaskName()));
		
	}
}
