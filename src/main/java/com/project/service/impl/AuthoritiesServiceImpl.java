package com.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.entity.Authorities;
import com.project.repository.AuthoRepo;
import com.project.service.AuthoritiesService;

@Service
public class AuthoritiesServiceImpl implements AuthoritiesService {

	@Autowired
	AuthoRepo repo;

	@Override
	public List<Authorities>  getRoleByUserId(Long userId) {
		// TODO Auto-generated method stub
		return repo.findByUserId(userId);
	}

	@Override
	public List<Authorities> findAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	@Transactional
	public void updateRoles(Long userId, Long roleId) {
		try {
			repo.updateRoles(roleId, userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

}
