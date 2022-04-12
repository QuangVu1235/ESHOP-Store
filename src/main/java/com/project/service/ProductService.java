package com.project.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.entity.Products;

public interface ProductService {
	List<Products> findAllAvailable();
	
	Products findById(Long productId);
	
	List<Products> findByTypeId(Long id);
	
	void updateQuantity(Integer newQuantity, Long productId);
	
	Page<Products> findByProductTypeSlug(String slug,Pageable Page);
	 
	List<Products> findByIsDeletedAndQuantityGreaterThan(Boolean isDeleted, Integer quantity, Pageable page);

	List<Products> findAll();

	Products update(Products product);

	List<Products> findByIsDeletedAndQuantityIsLessThanEqual(Boolean isDeleted, Integer quantity);

	Page<Products> findByNameLike(String name, Pageable pageable);

	List<Products> findByIsDeletedAndTypeIdAndQuantityGreaterThan(Boolean isDelete, Long TypeId, Integer quantity);
}
