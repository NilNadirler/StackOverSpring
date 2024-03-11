package com.example.stack.controllers;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.stack.services.ImageService;

@RestController
@RequestMapping("/api/image")
@CrossOrigin(maxAge = 3600)
public class ImageController {

	private ImageService imageService;

	public ImageController(ImageService imageService) {
		super();
		this.imageService = imageService;
	}
	
	@PostMapping("/{answerId}")
	public ResponseEntity<String> uploadFile(@RequestParam MultipartFile multipartFile,@PathVariable Long answerId){
		try {
			imageService.storeFile(multipartFile, answerId);
			return ResponseEntity.ok("Image stored successfully");
		}catch(IOException e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
		
	}
	
}
