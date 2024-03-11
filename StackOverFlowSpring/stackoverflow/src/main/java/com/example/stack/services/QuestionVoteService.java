package com.example.stack.services;

import java.util.Optional;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.springframework.stereotype.Service;

import com.example.stack.dto.QuestionVoteDto;
import com.example.stack.entity.QuestionVote;
import com.example.stack.entity.Questions;
import com.example.stack.entity.User;
import com.example.stack.enums.VoteType;
import com.example.stack.repositories.QuestionRepository;
import com.example.stack.repositories.QuestionVoteRepository;
import com.example.stack.repositories.UserRepository;

@Service
public class QuestionVoteService {
	
	
	private QuestionVoteRepository questionVoteRepository;
	
	private UserRepository userRepository;
	
	private QuestionRepository questionRepository;
	
	

	public QuestionVoteService(QuestionVoteRepository questionVoteRepository, UserRepository userRepository,
			QuestionRepository questionRepository) {
		super();
		this.questionVoteRepository = questionVoteRepository;
		this.userRepository = userRepository;
		this.questionRepository = questionRepository;
	}

	
	

	public QuestionVoteDto addVoteQuestion(QuestionVoteDto questionVoteDto) {
		
		Optional<User> optionalUser= userRepository.findById(questionVoteDto.getUserId());
		Optional<Questions> optionalQuestion= questionRepository.findById(questionVoteDto.getQuestionId());
		if(optionalUser.isPresent()&& optionalQuestion.isPresent()) {
			QuestionVote questionVote = new QuestionVote();
			Questions existingQuestion = optionalQuestion.get();
			questionVote.setQuestion(optionalQuestion.get());
			questionVote.setUser(optionalUser.get());
			questionVote.setVoteType(questionVoteDto.getVoteType());
			if(questionVote.getVoteType()==VoteType.UPVOTE) {
				existingQuestion.setVoteCount(existingQuestion.getVoteCount()+1);
			}else {
				existingQuestion.setVoteCount(existingQuestion.getVoteCount() -1);
			}   
			questionRepository.save(existingQuestion);
			QuestionVote votedQuestion = questionVoteRepository.save(questionVote);
			QuestionVoteDto questionVotedDto = new QuestionVoteDto();
			questionVotedDto.setId(votedQuestion.getId());
			return questionVotedDto;
		}
		
		
		
		return null;
	}






}
