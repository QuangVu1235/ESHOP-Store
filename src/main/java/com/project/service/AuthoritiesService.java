package com.project.service;

import java.util.List;

import com.project.entity.Authorities;

public interface AuthoritiesService {
	List<Authorities> getRoleByUserId(Long userId);

	List<Authorities> findAll();

	void updateRoles(Long userId, Long roleId);
}
