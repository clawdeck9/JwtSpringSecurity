package com.cluster9.jwtspringsecurity.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cluster9.jwtspringsecurity.entities.AppRole;

public interface AppRoleRepo extends JpaRepository<AppRole, Long>{
	public AppRole findByRoleName(String roleName);

}
