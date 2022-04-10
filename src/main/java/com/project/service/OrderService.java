package com.project.service;

import java.util.List;

import com.project.entity.Orders;

public interface OrderService {
	Orders insert(Orders orders);
	
	Orders findById(Long id);
	
	void update(Long id);
	
	void updateIsActive(Long id);

	List<Orders> findAll();
}
