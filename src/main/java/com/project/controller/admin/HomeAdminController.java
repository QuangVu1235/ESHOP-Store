package com.project.controller.admin;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.constant.SessionConst;
import com.project.entity.Users;
import com.project.service.UsersService;

@Controller
@RequestMapping("profile")
public class HomeAdminController {
	
	@Autowired
	private UsersService usersService;
	
	@GetMapping("user")
	public String getuser(HttpSession session,Authentication authentication) {
		if(authentication == null) {
			return "redirect:/login";
		}else {
			String username = authentication.getName();
			Users currentUser = usersService.findByUsername(username);
			session.setAttribute(SessionConst.CURRENT_USER, currentUser);
			return "admin/user";
		}
		
	}
	
	@GetMapping("profile")
	public String getProfile() {
		return "admin/page/profile";
	}
	
	@GetMapping("order")
	public String getOrder() {
		return "admin/page/order";
	}
	
	@GetMapping("password")
	public String getPass() {
		return "admin/page/updatepass";
	}
	

}
