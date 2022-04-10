package com.project.repository;

import com.project.dto.CartDetailDto;
import com.project.entity.OrderDetails;

import java.util.List;

import javax.persistence.criteria.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepo extends JpaRepository<OrderDetails, Long> {
	
	@Modifying(clearAutomatically = true)
	@Query(value = "INSERT INTO order_details(orderId, productId, price, quantity) "
			+ "VALUES(:#{#cartDetail.orderId}, :#{#cartDetail.productId}, "
			+ ":#{#cartDetail.price}, :#{#cartDetail.quantity})", 
			nativeQuery = true)
	void insert(@Param("cartDetail") CartDetailDto dto);
	
	@Query(value = "select top(10) productId from order_details group by productId ", nativeQuery = true)
	List<String> findTop10();
	

	List<OrderDetails> findTop10ByOrderByProductIdDesc();
	
	List<OrderDetails> findByOrderUserId(Long id);
}
