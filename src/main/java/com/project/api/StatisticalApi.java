package com.project.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.service.StatsService;

@RestController
@RequestMapping("/api/stas/")
public class StatisticalApi {
	
	@Autowired
	StatsService service;
	
	@GetMapping("/")
	public ResponseEntity<?> getAll(HttpSession session){
		String[][] chartData = service.getTotalPriceLast6Months();
		return ResponseEntity.ok(chartData);
		
		 
	}
}
