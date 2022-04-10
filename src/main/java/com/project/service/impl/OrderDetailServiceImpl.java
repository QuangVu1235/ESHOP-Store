package com.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dto.CartDetailDto;
import com.project.entity.OrderDetails;
import com.project.repository.OrderDetailsRepo;
import com.project.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
	
	@Autowired
	private OrderDetailsRepo repo;
	@Override
	public void insert(CartDetailDto dto) {
		// TODO Auto-generated method stub
		repo.insert(dto);
		
	}
	@Override
	public List<OrderDetails> findAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

}
