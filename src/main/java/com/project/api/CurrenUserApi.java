package com.project.api;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.entity.Users;
import com.project.service.UsersService;

@RestController
@RequestMapping("/api/currentuser")
public class CurrenUserApi {
	@Autowired
	UsersService usersService;
	
	@GetMapping("/")
	public ResponseEntity<?> getAll(HttpSession session,Authentication authentication){
		if(authentication == null) {
			return null;
		}
		String username = authentication.getName();
		Users currentUser = usersService.findByUsername(username) ;
		return ResponseEntity.ok(currentUser);
		
		 
	}
	
	@GetMapping("all")
	public ResponseEntity<?> doGetUserAll(){
		List<Users> users = usersService.findAll();
		return ResponseEntity.ok(users); 
	}
	
	@PutMapping("/{id}/{value}")
	public ResponseEntity<?> doGetUpdate(@PathVariable("id") String id, 
			@PathVariable("value") String value,@RequestBody Users users){
		try {
			usersService.updateUser(Integer.parseInt(value), users.getId());
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
