package com.project.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.constant.SessionConst;
import com.project.dto.CartDto;
import com.project.entity.Users;
import com.project.service.CartService;
import com.project.service.UsersService;
import com.project.util.SessionUtil;

@RestController
@RequestMapping("/api/cart")
public class CartApi {
	
	@Autowired
	CartService cartService;
	
	@Autowired
	UsersService usersService;
	
	//localhost:8080/api/cart/addtocard?productId=?&quantity=?&isReplace=?
	@GetMapping("/addtocard")
	public ResponseEntity<?> doGetAddtoCart(@RequestParam("productId") Long productId,
			@RequestParam("quantity") Integer quantity, @RequestParam("isReplace") Boolean isReplace,
			HttpSession session){
		CartDto currentCart = SessionUtil.getCurrentCart(session);
		
		cartService.updateCartnew(currentCart, productId, quantity, isReplace);
		
		session.setAttribute(SessionConst.CURRENT_CART, currentCart);
		
		return ResponseEntity.ok(currentCart);
		
	}
	
	
	// /api/cart/refresh
		@GetMapping("/refresh")
		public ResponseEntity<?> doGetRefresh(HttpSession session) {
			return ResponseEntity.ok(SessionUtil.getCurrentCart(session));
		}
		
		// /api/cart/checkout?address=...&phone=...
		@GetMapping("/checkout")
		@ResponseBody
		public ResponseEntity<?> doGetCheckOut(
				Authentication authentication,
				@RequestParam("address") String address,
				@RequestParam("phone") String phone,
				HttpSession session) {
			if(authentication == null) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
			String username = authentication.getName();
			Users currentUser = usersService.findByUsername(username) ;
			if (currentUser != null) {
				CartDto currentCart = SessionUtil.getCurrentCart(session);
				System.out.println(currentCart.getListDetail().values().isEmpty());
				try {
					if(currentCart.getListDetail().values().isEmpty() == true) {
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
					} 
						cartService.insert(currentCart, currentUser, address, phone);
						session.setAttribute(SessionConst.CURRENT_CART, new CartDto());
						return new ResponseEntity<>(HttpStatus.OK); // 200
				
				} catch (Exception ex) {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400
				}
			} else {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
			}
		}

}
