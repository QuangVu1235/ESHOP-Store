package com.project.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.repository.ProductTypesRepo;

@RestController
@RequestMapping("/api/type/")
public class ProductTypeApi {
	
	@Autowired
	ProductTypesRepo repo;
	
	
	@GetMapping("all")
	public ResponseEntity<?> doGetAddtoCart(){
		return ResponseEntity.ok(repo.findAll());
		
	}
}
