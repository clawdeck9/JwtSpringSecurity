package com.cluster9.jwtspringsecurity.security;

public class SecurityConstants {
	public static final String SECRET = "claude";
	public static final long EXPIRATION_TIME = 864000000; // 10 days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING ="Authorization"; 
}
