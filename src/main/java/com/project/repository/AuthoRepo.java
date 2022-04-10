package com.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.Authorities;

@Repository
public interface AuthoRepo extends JpaRepository<Authorities, Long> {
	List<Authorities> findByUserId(Long userId);
}
