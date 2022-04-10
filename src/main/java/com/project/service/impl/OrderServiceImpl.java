package com.project.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.entity.Orders;
import com.project.repository.OrdersRepo;
import com.project.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrdersRepo repo;

	@Override
	public Orders insert(Orders orders) {
		
		return repo.saveAndFlush(orders);
	}

	@Override
	public Orders findById(Long id) {
		
		Optional<Orders> optional = repo.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}

	@Override
	@Transactional
	public void update(Long id) {
		try {
			 repo.update(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	@Override
	@Transactional
	public void updateIsActive(Long id) {
		try {
			repo.updateIsActive(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		
		}
	}

	@Override
	public List<Orders> findAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	

	

}
