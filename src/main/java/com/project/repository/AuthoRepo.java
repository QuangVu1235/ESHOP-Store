package com.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.entity.Authorities;

@Repository
public interface AuthoRepo extends JpaRepository<Authorities, Long> {
	List<Authorities> findByUserId(Long userId);
	
	@Modifying(clearAutomatically = true)
	@Query(value="UPDATE authorities SET rolesId = ?1 where userId = ?2", nativeQuery = true)
	void updateRoles(Long rolesId, Long userId);
}
