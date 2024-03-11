package com.example.stack.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stack.dto.AnswerDto;
import com.example.stack.services.AnswerService;

@RestController
@RequestMapping("/api/answer")
@CrossOrigin(maxAge = 3600)
public class AnswerController {

	private AnswerService answerService;

	public AnswerController(AnswerService answerService) {
		super();
		this.answerService = answerService;
	}
	
	@PostMapping
	public ResponseEntity<?> addAnswer(@RequestBody AnswerDto answerDto){
		AnswerDto dto=answerService.postAnswer(answerDto);
		if(dto==null) return new ResponseEntity<>("Somethin went wrong", HttpStatus.BAD_REQUEST);
		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
	}
	
}
