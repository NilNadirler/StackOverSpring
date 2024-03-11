package com.example.stack.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.stack.dto.AllQuestionResponseDto;
import com.example.stack.dto.AnswerDto;
import com.example.stack.dto.QuestionDto;
import com.example.stack.dto.SingleQuestionDto;
import com.example.stack.entity.Answers;
import com.example.stack.entity.Questions;
import com.example.stack.entity.User;

import com.example.stack.repositories.AnswerRepository;
import com.example.stack.repositories.ImageRepository;
import com.example.stack.repositories.QuestionRepository;
import com.example.stack.repositories.UserRepository;

@Service
public class QuestionService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private ImageRepository imageRepository;
	
	public static final int SEARCH_RESULT_PER_PAGE=5;

	public QuestionDto addQuestion(QuestionDto questionDto) {
		Optional<User> optionalUser = userRepository.findById(questionDto.getUserId());
		if(optionalUser.isPresent()) {
			Questions question = new Questions();
			question.setTitle(questionDto.getTitle());
			question.setBody(questionDto.getBody());
			question.setTags(questionDto.getTags());
			question.setCreatedDate(new Date());
			question.setUser(optionalUser.get());
			Questions createdQuestions = questionRepository.save(question);
			QuestionDto createdQuestionDto = new QuestionDto();
			createdQuestionDto.setId(createdQuestions.getId());
			createdQuestionDto.setTitle(createdQuestions.getTitle());
			return createdQuestionDto;
			
		}
		
		return null;
	}

	public AllQuestionResponseDto getAllQuestions(int pageNumber) {
		Pageable paging = PageRequest.of(pageNumber,SEARCH_RESULT_PER_PAGE );
		Page<Questions> questionPage= questionRepository.findAll(paging);
		
		AllQuestionResponseDto allQuestionResponseDto= new AllQuestionResponseDto();
		allQuestionResponseDto.setQuestionDtoList(questionPage.getContent().stream().map(Questions::getQuestionDto).collect(Collectors.toList()));
		allQuestionResponseDto.setPageNumber(questionPage.getPageable().getPageNumber());
		allQuestionResponseDto.setTotalPages(questionPage.getTotalPages());
		
		return allQuestionResponseDto;
	}

	public SingleQuestionDto getQuestionById(Long questionId) {
	    Optional<Questions> optionalQuestion= questionRepository.findById(questionId);
	    if(optionalQuestion.isPresent()) {
	    	SingleQuestionDto singleQuestionDto=new SingleQuestionDto();
	    	List<AnswerDto> answerDtoList = new ArrayList();
	    	singleQuestionDto.setQuestionDto(optionalQuestion.get().getQuestionDto());
	    	List<Answers> answerList = answerRepository.findAllByQuestionId(questionId);
	    	for(Answers answer: answerList) {
	    		AnswerDto answerDto = answer.getAnswerDto();
	    		answerDto.setFile(imageRepository.findByAnswer(answer));
	    		answerDtoList.add(answerDto);
	    	}
	    	singleQuestionDto.setAnswerDtoList(answerDtoList);
	    	return singleQuestionDto;
	    }
	 
	
		return null;
	}

	public AllQuestionResponseDto getQuestionByUserId(Long userId, int pageNumber) {
		Pageable paging = PageRequest.of(pageNumber,SEARCH_RESULT_PER_PAGE );
		Page<Questions> questionPage= questionRepository.findAll(paging);
		
		AllQuestionResponseDto allQuestionResponseDto= new AllQuestionResponseDto();
		allQuestionResponseDto.setQuestionDtoList(questionPage.getContent().stream().map(Questions::getQuestionDto).collect(Collectors.toList()));
		allQuestionResponseDto.setPageNumber(questionPage.getPageable().getPageNumber());
		allQuestionResponseDto.setTotalPages(questionPage.getTotalPages());
		
		return allQuestionResponseDto;
	}

	
}
