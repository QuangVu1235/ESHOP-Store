package com.project.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.constant.SessionConst;
import com.project.dto.ChagePass;
import com.project.entity.Users;
import com.project.service.UsersService;

@RestController
@RequestMapping("/api/user/")
public class UserApi {
	
	@Autowired
	UsersService service;
	
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
