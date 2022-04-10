package com.project.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.entity.Authorities;
import com.project.entity.Users;
import com.project.service.AuthoritiesService;
import com.project.service.UsersService;

@Service
public class UserDetailSeriveImpl implements UserDetailsService {
	
	@Autowired
	UsersService repo;
	
	@Autowired
	AuthoritiesService authoritiesService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Users appUser = repo.findByUsername(username);
        if (appUser == null) {
            System.out.println("User not found! " + username);
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
        List<Authorities>  autho = authoritiesService.getRoleByUserId(appUser.getId());
        ArrayList<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        for(Authorities auth : autho) {
    	if (autho != null) {
            // ROLE_USER, ROLE_ADMIN,..
            GrantedAuthority authority = new SimpleGrantedAuthority(auth.getRole().getName());
            grantList.add(authority);
       
    	}
    
    }
        UserDetails userDetails = (UserDetails) new User(appUser.getUsername(),appUser.getHashPassword(), grantList);
        return userDetails;
	}

}
