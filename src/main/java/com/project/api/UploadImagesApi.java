package com.project.api;

import java.io.File;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.service.UploadService;

@CrossOrigin("*")
@RestController
public class UploadImagesApi {
	@Autowired
	UploadService uploadService;
	
	@PostMapping("/api/upload/images/{folder}")
	public JsonNode upload(@PathVariable("file") MultipartFile file,
			@PathVariable("folder") String folder) {
		try {
			File saveFile = uploadService.save(file,folder,"images");
			System.out.println(saveFile.getName());
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode node = mapper.createObjectNode();
			
			node.put("name", saveFile.getName());
			node.put("size", saveFile.length());
			return node;
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	@PostMapping("/api/upload/images/product/{folder}")
	public JsonNode uploadProduct(@PathVariable("file") MultipartFile file,
			@PathVariable("folder") String folder) {
		try {
			File saveFile = uploadService.save(file,folder,"images/products");
			System.out.println(saveFile.getName());
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode node = mapper.createObjectNode();
			
			node.put("name", saveFile.getName());
			node.put("size", saveFile.length());
			return node;
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
		
	}
}
