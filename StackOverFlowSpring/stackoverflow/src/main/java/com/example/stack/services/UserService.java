package com.example.stack.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.stack.dto.SignupDto;
import com.example.stack.dto.UserDto;
import com.example.stack.entity.User;
import com.example.stack.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	
	public UserDto createUser(SignupDto signupDto) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setEmail(signupDto.getEmail());
		user.setName(signupDto.getName());
		user.setPassword(new BCryptPasswordEncoder().encode(signupDto.getPassword()));
		User created =userRepository.save(user);
		UserDto userDto = new UserDto();
		userDto.setId(created.getId());
		
		return userDto;
		
	}
	
	public boolean hasUsernWithEmail(String email) {
		 return userRepository.findFirstByEmail(email).isPresent();

}
}