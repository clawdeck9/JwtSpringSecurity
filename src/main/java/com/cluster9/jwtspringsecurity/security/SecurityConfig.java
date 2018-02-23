package com.cluster9.jwtspringsecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private UserDetailsService uds;
	@Autowired
	private BCryptPasswordEncoder bcEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*auth.inMemoryAuthentication()
			.withUser("admin").password("admin").roles("ADMIN","USER")
			.and()
			.withUser("user").password("user").roles("USER");*/
		/*auth.jdbcAuthentication()
			.usersByUsernameQuery("an sql query")
			.authoritiesByUsernameQuery("");*/
		auth.userDetailsService(uds)
			.passwordEncoder(bcEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable(); // the synchroniser token mechanism won't be used, the jwt will be instead
		http.formLogin();
		http.authorizeRequests().antMatchers("/login/**", ":register/**");
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/tasks/**")
			.hasAuthority("ADMIN");
		http.authorizeRequests().anyRequest().authenticated();
	}
}
