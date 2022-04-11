package com.project.repository;

import com.project.entity.Orders;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepo extends JpaRepository<Orders, Long> {
	List<Orders> findByUserId(Long id);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE orders SET isActive = 1 where id = ?", nativeQuery = true)
	void  update(Long id);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE orders SET isActive = 0 where id = ?", nativeQuery = true)
	void  updateIsActive(Long id);
	
	@Query(value = "{CALL sp_getTotalPricePerMonth(:month, :year)}", nativeQuery = true)
	String getToTalPricePerMonth(@Param("month") String month,@Param("year") String year);
}
