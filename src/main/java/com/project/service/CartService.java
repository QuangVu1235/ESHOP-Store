package com.project.service;

import com.project.dto.CartDto;
import com.project.entity.Users;

public interface CartService {
	//them moi
	CartDto updateCartnew(CartDto cart, Long productId, Integer quantity, boolean isReplace);
	
	Integer getTotalQuantity(CartDto cart);
	
	Double getTotalPrice(CartDto cart);
	
	void insert(CartDto cart, Users user, String address, String phone) throws Exception;
	
}
