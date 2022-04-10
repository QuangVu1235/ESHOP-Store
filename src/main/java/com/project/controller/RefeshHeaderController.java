package com.project.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.constant.SessionConst;
import com.project.dto.CartDto;
import com.project.service.CartService;

@Controller
@RequestMapping("/cart")

public class RefeshHeaderController {
	
	@Autowired
	CartService cartService;
	
	@GetMapping("/update")
	public String doGetUpdate(HttpSession session) {
		CartDto currentCart = (CartDto) session.getAttribute(SessionConst.CURRENT_CART);
		session.setAttribute(SessionConst.CURRENT_CART, currentCart);
		return "user/fragments/header::#viewCartFragment";
	}
	
	@GetMapping("/newupdate")
	public String doGetCart(
			@RequestParam("productId") Long productId,
			@RequestParam("quantity") Integer quantity,
			@RequestParam("isReplace") Boolean isReplace,
			HttpSession session
								) {
		CartDto currentCart = (CartDto) session.getAttribute(SessionConst.CURRENT_CART);
		cartService.updateCartnew(currentCart, productId, quantity, isReplace);
		session.setAttribute(SessionConst.CURRENT_CART, currentCart);
		return "user/cart::#cartFragment";
	}
	
}
