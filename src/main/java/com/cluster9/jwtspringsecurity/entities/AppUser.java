package com.cluster9.jwtspringsecurity.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class AppUser {
	
	@Id @GeneratedValue
	private Long id;
	@Column(unique=true)
	private String username;
	private String password;
	@ManyToMany(fetch=FetchType.EAGER)
	private Collection<AppRole> roles=new ArrayList<>();

}
