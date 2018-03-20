package com.cluster9.jwtspringsecurity.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cluster9.jwtspringsecurity.entities.Task;
// import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// @RepositoryRestResource
public interface TaskRepo extends JpaRepository<Task, Long>{
	
}
