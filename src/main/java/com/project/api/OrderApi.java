package com.project.api;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.entity.OrderDetails;
import com.project.entity.Users;
import com.project.repository.OrderDetailsRepo;
import com.project.repository.OrdersRepo;
import com.project.service.OrderService;
import com.project.service.UsersService;

@RestController
@RequestMapping("/api/order")
public class OrderApi {
	
	@Autowired
	private OrderDetailsRepo repo;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired 
	private OrderService OrderService;
	
	@Autowired
	private OrdersRepo ordersRepo;
	
	@GetMapping("/list")
	public ResponseEntity<?> doGetOrderList(HttpSession session, Authentication authentication,Model  model){
		
		if(authentication == null) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		String username = authentication.getName();
		Users currentUser = usersService.findByUsername(username) ;	
		List<OrderDetails> orders = repo.findByOrderUserId(currentUser.getId());
		return ResponseEntity.ok(orders);
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> doGetOrderAllList(HttpSession session, Authentication authentication,Model  model){
		if(authentication == null) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		List<OrderDetails> orders = repo.findAll();
		return ResponseEntity.ok(orders);	
	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<?> doGetPut(@PathVariable("id") String id) {
		try {
			OrderService.update(Long.parseLong(id));
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("active/{id}")
	public ResponseEntity<?> doGetUpdateIsActice(@PathVariable("id") String id) {
		try {
			OrderService.updateIsActive(Long.parseLong(id));
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("getall")
	public ResponseEntity<?> getall() {
		
		return ResponseEntity.ok(ordersRepo.findAll());
	}
}
