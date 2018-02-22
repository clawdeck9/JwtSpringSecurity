package com.cluster9.jwtspringsecurity.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cluster9.jwtspringsecurity.entities.AppUser;

public interface AppUserRepo extends JpaRepository<AppUser, Long>{
	public AppUser findByUsername(String username);
}
