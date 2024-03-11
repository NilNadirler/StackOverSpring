package com.example.stack.dto;

import java.util.List;

public class AllQuestionResponseDto {
	
	private List<QuestionDto> questionDtoList;
	
	private Integer totalPages;
	
	private Integer pageNumber;
	
	

	public List<QuestionDto> getQuestionDtoList() {
		return questionDtoList;
	}

	public void setQuestionDtoList(List<QuestionDto> questionDtoList) {
		this.questionDtoList = questionDtoList;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	

}
