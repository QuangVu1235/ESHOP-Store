package com.project.api;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.constant.SessionConst;
import com.project.dto.ChagePass;
import com.project.entity.Authorities;
import com.project.entity.Users;
import com.project.service.AuthoritiesService;
import com.project.service.UsersService;

@RestController
@RequestMapping("/api/user/")
public class UserApi {
	
	@Autowired
	UsersService service;
	
	@Autowired
	AuthoritiesService authoritiesService;
	
	@GetMapping("auth")
	public ResponseEntity<?> doGetAll() {
		List<Authorities> auths = authoritiesService.findAll();
		return ResponseEntity.ok(auths);
	}
	
	@PutMapping("role/{userId}/{roleId}")
	public ResponseEntity<?> doPostRoles(@PathVariable("userId") String userId, @PathVariable("roleId") String roleId
			,@RequestBody Authorities authorities) {
		try {
			authoritiesService.updateRoles(Long.parseLong(userId),Long.parseLong(roleId));
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("{id}")
	public Users putUser(@PathVariable("id") String id, @RequestBody Users user) {
		return service.update(user);
	}
	
	@PutMapping("password/{id}")
	public Users newPass(@PathVariable("id") String id, @RequestBody ChagePass chagePass,HttpSession session) {
		Users  user = (Users) session.getAttribute(SessionConst.CURRENT_USER) ;
		Users userrequest = service.updatePassWord(user,chagePass);
		return userrequest;
		
	}
}
