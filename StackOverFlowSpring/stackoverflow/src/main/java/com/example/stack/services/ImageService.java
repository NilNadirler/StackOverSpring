package com.example.stack.services;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.stack.entity.Answers;
import com.example.stack.entity.Image;
import com.example.stack.repositories.AnswerRepository;
import com.example.stack.repositories.ImageRepository;

@Service
public class ImageService {
	
	private ImageRepository imageRepository;
	private AnswerRepository answerRepository;
	
	
	public ImageService(ImageRepository imageRepository, AnswerRepository answerRepository) {
		super();
		this.imageRepository = imageRepository;
		this.answerRepository = answerRepository;
	}


	public void storeFile(MultipartFile multipartFile, Long answerId)throws IOException {
		Optional<Answers> optionalAnswer= answerRepository.findById(answerId);
		if(optionalAnswer.isPresent()) {
			String fileName= StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
			Image image = new Image();
			 image.setName(fileName);
			 image.setAnswer(optionalAnswer.get());
			 image.setType(multipartFile.getContentType());
			 image.setData(multipartFile.getBytes());
			 imageRepository.save(image);
			 
		}else {
			throw new IOException("Answer not found");
		}
		
	}

}
