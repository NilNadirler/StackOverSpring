package com.example.stack.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.stack.dto.AnswerDto;
import com.example.stack.entity.Answers;
import com.example.stack.entity.Questions;
import com.example.stack.entity.User;
import com.example.stack.repositories.AnswerRepository;
import com.example.stack.repositories.QuestionRepository;
import com.example.stack.repositories.UserRepository;

@Service
public class AnswerService {

	private UserRepository userRepository;
	private QuestionRepository questionRepository;
	private AnswerRepository asnwerRepository;
	
	
	public AnswerService(UserRepository userRepository, QuestionRepository questionRepository,
			AnswerRepository asnwerRepository) {
		super();
		this.userRepository = userRepository;
		this.questionRepository = questionRepository;
		this.asnwerRepository = asnwerRepository;
	}



	public AnswerDto postAnswer(AnswerDto answerDto) {
		Optional<User> optionalUser= userRepository.findById(answerDto.getUserId());
		Optional<Questions> optionalQuestions= questionRepository.findById(answerDto.getQuestionId());
		
		if(optionalUser.isPresent() && optionalQuestions.isPresent()) {
			Answers answer = new Answers();
			answer.setBody(answerDto.getBody());
			answer.setCreatedDate(new Date());
			answer.setUser(optionalUser.get());
			answer.setQuestion(optionalQuestions.get());
			Answers createdAnswers= asnwerRepository.save(answer);
			AnswerDto createdAnswerDto = new AnswerDto();
			createdAnswerDto.setId(createdAnswers.getId());
			return createdAnswerDto;
		}
		return null;
	}

}
