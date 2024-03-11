package com.example.stack.controllers;

import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stack.configuration.JwtUtil;
import com.example.stack.configuration.UserDetailsServiceImpl;
import com.example.stack.dto.AuthenticationRequest;
import com.example.stack.dto.SignupDto;
import com.example.stack.dto.UserDto;
import com.example.stack.entity.User;
import com.example.stack.repositories.UserRepository;
import com.example.stack.services.UserService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(maxAge = 3600)
public class SignupController {
	public static final String TOKEN_PREFIX ="Authorization Bearer ";
	
	@Autowired
	private UserService userService;	
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	private UserRepository userRepository;
	
    
	@PostMapping("/sign-up")
	public ResponseEntity<?> createUser(@RequestBody SignupDto signupDto){
	
		if(userService.hasUsernWithEmail(signupDto.getEmail())) {
			return new ResponseEntity<>("This email already taken",HttpStatus.NOT_ACCEPTABLE);	
		}else {
			UserDto createdUser = userService.createUser(signupDto);
			 return new ResponseEntity<>(createdUser,HttpStatus.CREATED);
		}
	}
	
	@PostMapping("/authenticate")
	public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticaionRequest, HttpServletResponse response) throws IOException {
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticaionRequest.getEmail(),authenticaionRequest.getPassword()));
			
		}catch(BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect username pr password");
			
		}catch(DisabledException disabledException) {
			try {
				response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,"User is not found");
			} catch (java.io.IOException e) {
				// TODO Auto-generated
				e.printStackTrace();
			}
			return;
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticaionRequest.getEmail());
		Optional<User> user = userRepository.findFirstByEmail(authenticaionRequest.getEmail());
		final String jwt = jwtUtil.generateToken(authenticaionRequest.getEmail());
		 //return new AuthenticationResponse(jwt);
		
		try {
			response.getWriter().write(new JSONObject()
			  .put("userId", user.get().getId())

			  .put("token", jwt)
			  .toString()
			  );
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (java.io.IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.addHeader("Access-Control-Expose-Headers", "Authorization");
		response.addHeader("Access-Control-Allow-Headers", "Authorization, X-PINGGOTHER, Origin, X-Requested-With,Content-Type,Accept,X-Custom-header");
		response.addHeader(TOKEN_PREFIX,jwt);
		  
	}
}
