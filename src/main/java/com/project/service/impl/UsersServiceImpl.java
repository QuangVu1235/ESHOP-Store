package com.project.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.dto.ChagePass;
import com.project.entity.Users;
import com.project.repository.UsersRepo;
import com.project.service.UsersService;


@Service
public class UsersServiceImpl implements UsersService {
	
	private BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

    @Autowired
    UsersRepo repo;
    
    
    @Override
    public List<Users> findAll() {
        return repo.findAll();
    }

    @Override
    public Users findById(Long id) {
        Optional<Users> optional =  repo.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }


	@Override
	public Users findByUsername(String username) {
		// TODO Auto-generated method stub
		return repo.findByUsername(username);
	}
	
	@Override
	public Users doLogin(String username, String password) {
		Users user = repo.findByUsername(username);
		
		if (user != null) {
			String hashPassword = user.getHashPassword(); // pw da ma hoa trong DB
			boolean checkPassword = bcrypt.matches(password, hashPassword);
			return checkPassword ? user : null;
		} else {
			return null;
		}
	}

	@Override
	//Co Transactionnal
	@Transactional
	public Users save(Users user) throws SQLException {
		String hashPassword = bcrypt.encode(user.getHashPassword());
		user.setHashPassword(hashPassword);
		user.setIsDeleted(Boolean.FALSE);
		user.setImgUrl("defaul");
		return repo.saveAndFlush(user);
	}
	
	@Override
	@Transactional	
	public Users update(Users user) {
		// TODO Auto-generated method stub
		return repo.saveAndFlush(user);
	}

	@Override
	@Transactional
	public Users updatePassWord(Users user, ChagePass chagePass) {
		// TODO Auto-generated method stub
		String oldpass = chagePass.getOldpass();
		String newpass = chagePass.getNewpass();
		String hasPass = user.getHashPassword();
		
		Boolean checkPass = bcrypt.matches(oldpass, hasPass);
		try {
			if(checkPass == true) {
				String hashPassword = bcrypt.encode(newpass);
				user.setHashPassword(hashPassword);
				return repo.saveAndFlush(user);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public void updateUser(Integer isDeleted, Long userId) {
		// TODO Auto-generated method stub
		try {
			repo.updateUser(isDeleted, userId);
		} catch (Exception e) {
			// TODO: handle exception
		}	 
	}

	
	}

