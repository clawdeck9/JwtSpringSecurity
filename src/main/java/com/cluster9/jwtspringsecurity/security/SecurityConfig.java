package com.cluster9.jwtspringsecurity.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// a configuration class like this one can be replaced by a config xml file
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private UserDetailsService uds;
	@Autowired
	private BCryptPasswordEncoder bcEncoder;
	
	
	// add a uds and an encoder to the auth manager
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
	
	// here we want to use a non-loginForm mechanism that allows a client (another server) to call this service: it's a REST api
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable(); // the synchronizer token mechanism won't be used, the jwt will be used instead
		//disable the cookie auth mechanism 
		// from reference auth to value authentication
		// http.formLogin();
		http.cors();     //  This enables cors because the browser wants it!
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers("/login/**").permitAll();
		// all the tasks access requires Admin authority
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/tasks/**").hasAuthority("ADMIN");
		// all other tasks require authentication, 
		// means authenicationToken added to the secContext in the authauFilter
		http.authorizeRequests().anyRequest().authenticated();
		// filter chain:
		http.addFilter(new JwtAuthenticationFilter(authenticationManager()));
		// add the authorize filter before the usernamepasswordauthfilter:
		http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
	}
	
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

	
	@Bean
	public AccessDeniedHandler accessDeniedHandler(){
	    return new CustomAccessDeniedHandler();
	}
	 
	
}
