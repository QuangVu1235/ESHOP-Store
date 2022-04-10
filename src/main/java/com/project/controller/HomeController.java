package com.project.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.entity.Authorities;
import com.project.entity.OrderDetails;
import com.project.entity.Products;
import com.project.entity.Roles;
import com.project.entity.Users;
import com.project.repository.AuthoRepo;
import com.project.repository.OrderDetailsRepo;
import com.project.service.ProductService;
import com.project.service.ProductTypesService;
import com.project.service.UsersService;

@Controller
@RequestMapping("/")
public class HomeController {
	@Autowired
	UsersService usersService;
	
	@Autowired
	ProductService productService;

	@Autowired
	ProductTypesService productTypesService;
	
	@Autowired
	AuthoRepo authoRepo;
	
	@Autowired
	OrderDetailsRepo repo;
	
	@GetMapping("index")
	public String getIndex(Model model, Authentication authentication, HttpSession session) {
		if(authentication == null) {
			
		}else {
			String auth= authentication.getAuthorities().toString();
			session.setAttribute("role", auth);
			System.out.println(auth);
		}
		
		
		Pageable pageable = PageRequest.of(0, 8);
		
		List<Products> products = productService.findByIsDeletedAndQuantityGreaterThan(Boolean.FALSE, 0, pageable);
		
		List<Products> productTypes_smartphone = productService.findByTypeId((long) 1);
		List<Products> productTypes_laptop = productService.findByTypeId((long) 2);
		
		List<OrderDetails> test = repo.findTop10ByOrderByProductIdDesc();
		

		model.addAttribute("smartphones",productTypes_smartphone);
		model.addAttribute("laptops",productTypes_laptop);
		model.addAttribute("products",products);
		return "user/index";
	}
	
	@RequestMapping("/login")
	public String doGetLogin() {
		return "user/accounts/login";
	}
	
	@GetMapping("/404")
	public String get403() {
		return "user/404";
	}
	
	@GetMapping("register")
	public String getRegister(Model model,@ModelAttribute("userRegister") Users userRegister) {
		return "user/accounts/register";
	}
	
	@GetMapping("cart")
	public String getCart() {
		return "user/cart";
	}
	
	@PostMapping("register")
	public String doPostRegister(@ModelAttribute("userRegister") Users userRegister, HttpSession session) {
		System.out.println(userRegister);
		try {
			Users userResponse = usersService.save(userRegister);
			
			Authorities authorities = new Authorities();
			authorities.setRole(new Roles(1L,"CUST","Customers"));
			authorities.setUser(userResponse);
			
			authoRepo.saveAndFlush(authorities);
			if (userResponse != null) {
				
				return "redirect:/index";
			} else {
				return "redirect:/register";
			}
		} catch (Exception e) {
			// TODO: handle exception
			return null; 
		}
		
		
	}

}
