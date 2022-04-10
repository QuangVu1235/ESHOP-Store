package com.project.service;

import java.util.List;

import com.project.dto.CartDetailDto;
import com.project.entity.OrderDetails;

public interface OrderDetailService {
	
	void insert(CartDetailDto dto);
	
	List<OrderDetails> findAll();
}
