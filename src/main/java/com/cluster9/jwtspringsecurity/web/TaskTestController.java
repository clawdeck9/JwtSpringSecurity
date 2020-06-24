package com.cluster9.jwtspringsecurity.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cluster9.jwtspringsecurity.dao.TaskRepo;
import com.cluster9.jwtspringsecurity.entities.Task;

//@CrossOrigin(maxAge = 3600)
@RestController
public class TaskTestController {
	@Autowired
	private TaskRepo tr;
	
	@GetMapping("/tasks")
	public List<Task> getTasks(){
		return tr.findAll();
	}
	
	@PostMapping("/tasks")
	public Task save(@RequestBody Task t){
		return tr.save(t);
	}
}
