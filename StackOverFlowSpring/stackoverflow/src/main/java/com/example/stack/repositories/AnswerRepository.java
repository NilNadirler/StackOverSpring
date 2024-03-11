package com.example.stack.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.stack.entity.Answers;

public interface AnswerRepository extends JpaRepository<Answers,Long> {

	List<Answers> findAllByQuestionId(Long questionId);

}
