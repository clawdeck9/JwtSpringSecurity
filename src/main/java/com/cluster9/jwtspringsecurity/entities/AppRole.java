package com.cluster9.jwtspringsecurity.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@Data @NoArgsConstructor @AllArgsConstructor
public class AppRole {
	public AppRole() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AppRole(Long id, String roleName) {
		super();
		this.id = id;
		this.roleName = roleName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	@Id @GeneratedValue
	private Long id;
	private String roleName;
}
