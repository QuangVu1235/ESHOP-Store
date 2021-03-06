package com.project.controller.admin;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.constant.SessionConst;
import com.project.entity.Users;
import com.project.service.UsersService;

@Controller
@PreAuthorize("hasAnyRole('ROLE_DIRE','ROLE_STAF')")
@RequestMapping("admin")
public class HomeAdminController2 {
	
	@Autowired
	private UsersService usersService;
	@GetMapping("index")
	public String getuser(HttpSession session,Authentication authentication) {
		if(authentication == null) {
			return "redirect:/login";
		}else {
			String username = authentication.getName();
			Users currentUser = usersService.findByUsername(username);
			session.setAttribute(SessionConst.CURRENT_USER, currentUser);
			return "admin/admin";
		}
	}
	@GetMapping("order")
	public String getOrder() {
		return "admin/pages/order";
	}
	
	@GetMapping("product")
	public String getProduct() {
		return "admin/pages/product";
	}
	
	@GetMapping("profile")
	public String getProfile() {
		return "admin/pages/profile";
	}
	
	
	@GetMapping("password")
	public String getPass() {
		return "admin/pages/updatepass";
	}
	
	@GetMapping("user")
	public String getUser() {
		return "admin/pages/usermanager";
	}
	
	@GetMapping("auth")
	public String getAuth() {
		return "admin/pages/auth";
	}
	
	@GetMapping("stas")
	public String getStas() {
		return "admin/pages/stas";
	}
	
	@GetMapping("category")
	public String getCategory() {
		return "admin/pages/category";
	}

}
