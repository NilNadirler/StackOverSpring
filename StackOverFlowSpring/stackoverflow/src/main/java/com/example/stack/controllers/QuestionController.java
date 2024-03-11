package com.example.stack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stack.dto.AllQuestionResponseDto;
import com.example.stack.dto.QuestionDto;
import com.example.stack.dto.SingleQuestionDto;
import com.example.stack.services.QuestionService;

@RestController
@RequestMapping("/api/question")
@CrossOrigin(maxAge = 3600)
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;
	
	
	@PostMapping
	public ResponseEntity<?> postQuestion(@RequestBody QuestionDto questionDto){
		QuestionDto createdQuestionDto = questionService.addQuestion(questionDto);
		if(createdQuestionDto==null) {
			return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
			
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestionDto);
	}
	
	@GetMapping("/questions/{pageNumber}")
	public ResponseEntity<AllQuestionResponseDto> getAllQuestions(@PathVariable int pageNumber){
		
		AllQuestionResponseDto  responseDto= questionService.getAllQuestions(pageNumber); 
		return ResponseEntity.ok(responseDto);
	}
	
	@GetMapping("/{questionId}")
	public ResponseEntity<?> qetQuestionById(@PathVariable Long questionId){
		SingleQuestionDto singleQuestionDto = questionService.getQuestionById(questionId);
		if(singleQuestionDto==null)return ResponseEntity.notFound().build();
		return ResponseEntity.ok(singleQuestionDto);
		
	}
	
	@GetMapping("/{userId}/{pageNumber}")
	public ResponseEntity<?> qetQuestionUserById(@PathVariable Long userId, @PathVariable int pageNumber){
		AllQuestionResponseDto singleQuestionDto = questionService.getQuestionByUserId(userId,pageNumber);
		if(singleQuestionDto==null)return ResponseEntity.notFound().build();
		return ResponseEntity.ok(singleQuestionDto);
		
	}

}
