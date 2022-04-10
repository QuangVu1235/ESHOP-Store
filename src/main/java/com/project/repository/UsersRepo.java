package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.entity.Users;

@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {

    @Query("select u from Users u where u.username = ?1 and u.hashPassword = ?2")
    Users findByUsernameAndHashPassword(String username, String password);
    
    Users findByUsername(String username);
    
    @Modifying(clearAutomatically = true)
	@Query(value = "UPDATE users SET isDeleted = ?1 WHERE id = ?2", nativeQuery = true)
    void updateUser(Integer isDeleted, Long userId);
}

