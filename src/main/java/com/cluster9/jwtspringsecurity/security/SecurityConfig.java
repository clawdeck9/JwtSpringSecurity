package com.cluster9.jwtspringsecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// a configuration class like this one can be replaced by a config xml file
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
		//disable the cookie auth mechanism 
		// from reference auth to value authentication
		//http.formLogin();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers("/login/**", ":register/**");
		// all the tasks access requires Admin authority
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/tasks/**").hasAuthority("ADMIN");
		// all other tasks requires authentification, 
		// means authenicationToken added to the secContext in the authauFilter
		http.authorizeRequests().anyRequest().authenticated();
		// filter chain:
		http.addFilter(new JwtAuthenticationFilter(authenticationManager()));
		// add the authaurise filter before the usernamepasswordauthfilter:
		http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
