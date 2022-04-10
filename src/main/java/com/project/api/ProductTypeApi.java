package com.project.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.constant.SessionConst;
import com.project.dto.CartDto;
import com.project.entity.ProductTypes;
import com.project.repository.ProductTypesRepo;
import com.project.util.SessionUtil;

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
