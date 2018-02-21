package com.cluster9.jwtspringsecurity;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cluster9.jwtspringsecurity.dao.TaskRepo;
import com.cluster9.jwtspringsecurity.entities.Task;

@SpringBootApplication
public class JwtSpringSecurityApplication implements CommandLineRunner{
	@Autowired
	TaskRepo tr;

	public static void main(String[] args) {
		SpringApplication.run(JwtSpringSecurityApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		Stream.of("t1","t2","t3").forEach(t -> tr.save(new Task(null, t)));
		tr.findAll().forEach(t -> System.out.println("name: " + t.getTaskName()));
		
	}
}
