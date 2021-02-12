package com.ps.springbootsampleapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ps.springbootsampleapp.models.AuthenticationRequest;
import com.ps.springbootsampleapp.models.AuthenticationResponse;
import com.ps.springbootsampleapp.security.MyUserDetailsService;
import com.ps.springbootsampleapp.util.JwtUtil;

@RestController
@RequestMapping("/security/services/v1/")
public class TokenProvider {
	
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	MyUserDetailsService uds;
	
	@Autowired
	JwtUtil jwtUtil;
	
		
	@PostMapping("/token")
	public String token(@RequestBody AuthenticationRequest request) throws Exception {
		try {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
	} catch (Exception e) {
		throw new Exception ("Incorrect Username/password");
	}
		final UserDetails ud = uds.loadUserByUsername(request.getUserName());
		final String jwt = jwtUtil.generateToken(ud);
		return jwt;
	}
}
