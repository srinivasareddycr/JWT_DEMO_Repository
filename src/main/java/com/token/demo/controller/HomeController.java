package com.token.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.token.demo.model.JwtRequest;
import com.token.demo.model.JwtResponse;
import com.token.demo.service.UserService;
import com.token.demo.utility.JWTUtility;

@RestController
public class HomeController {
	
	@Autowired
	private JWTUtility jwtUtility;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;

	@GetMapping("/")
	public  String home()
	{
		return " Welcome to JWT DEMO ";
	}

	
	@PostMapping("/authenticate")
	public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest ) throws Exception
	{
		try {
			authenticationManager.authenticate(

					new  UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword()));
		}catch(BadCredentialsException e)
		{
			 throw new Exception("invalid credentials" ,e);
			
			
		}
		final UserDetails userDetails =  userService.loadUserByUsername(jwtRequest.getUserName());
		final String token= jwtUtility.generateToken(userDetails);
		return  new JwtResponse(token);
		}
	
}
