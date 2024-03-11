package com.example.stack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.stack.entity.Answers;
import com.example.stack.entity.Image;

public interface ImageRepository extends JpaRepository<Image,Long>{

	Image findByAnswer(Answers answer);

}
