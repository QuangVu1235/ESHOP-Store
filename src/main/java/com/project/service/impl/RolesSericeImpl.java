package com.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.entity.Roles;
import com.project.repository.RolesRepo;
import com.project.service.RolesService;

public class RolesSericeImpl implements RolesService {
	
	@Autowired
	RolesRepo repo;

	@Override
	public List<String> finByUsername(Long userId) {
		// TODO Auto-generated method stub
		return repo.findbyName(userId);
	}

}
