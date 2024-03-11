package com.example.stack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.stack.entity.QuestionVote;

public interface QuestionVoteRepository extends JpaRepository<QuestionVote,Long>{

}
