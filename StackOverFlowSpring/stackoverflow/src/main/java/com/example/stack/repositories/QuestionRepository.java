package com.example.stack.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.stack.entity.Questions;

public interface QuestionRepository extends JpaRepository<Questions,Long> {

}
