package com.project.service;

import java.sql.SQLException;
import java.util.List;

import com.project.dto.ChagePass;
import com.project.entity.Users;

public interface UsersService {
	
	List<Users> findAll();

	Users findById(Long id);

//	Users findByUsernameAndHashPassword(String username, String password);
	
	Users findByUsername(String username);
	
	Users doLogin(String username, String password);

	Users save(Users user) throws SQLException;

	Users update(Users user);

	Users updatePassWord(Users user, ChagePass chagePass);

	void updateUser(Integer isDeleted, Long userId);
	

}
