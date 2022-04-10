package com.project.repository;

import com.project.entity.Roles;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepo extends JpaRepository<Roles, Long> {
	
	@Modifying(clearAutomatically = true)
	@Query(value ="select roles.name FROM \r\n"
			+ "authorities join roles \r\n"
			+ "on authorities.rolesId = roles.id \r\n"
			+ "where userId = ?", nativeQuery = true)
	List<String> findbyName(Long userId);
}
