package com.example.stack.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stack.dto.QuestionVoteDto;
import com.example.stack.services.QuestionVoteService;

@RestController
@RequestMapping("/api")
@CrossOrigin(maxAge = 3600)
public class VoteController {

	private QuestionVoteService voteService;
	
	
	
	
	public VoteController(QuestionVoteService voteService) {
		super();
		this.voteService = voteService;
	}




	@PostMapping("/question/vote")
	public ResponseEntity<?> addVoteToQuestion(@RequestBody QuestionVoteDto questionVoteDto){
		QuestionVoteDto questionVotedDto = voteService.addVoteQuestion(questionVoteDto);
		if(questionVotedDto == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Somethin went wrong");
		return ResponseEntity.status(HttpStatus.CREATED).body(questionVotedDto);
	}
}
