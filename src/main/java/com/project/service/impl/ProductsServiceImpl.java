package com.project.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.entity.Products;
import com.project.repository.ProductsRepo;
import com.project.service.ProductService;

@Service
public class ProductsServiceImpl implements ProductService  {
	
	@Autowired
	ProductsRepo productsRepo;
	@Override
	public List<Products> findAllAvailable() {
		// TODO Auto-generated method stub
		return productsRepo.findByIsDeletedAndQuantityGreaterThan(Boolean.FALSE, 0);
	}
	@Override
	public Products findById(Long productId) {
		// TODO Auto-generated method stub
		Optional<Products> optional = productsRepo.findById(productId);
		return optional.isPresent() ? optional.get() : null;
		
	}
	@Override
	public List<Products> findByTypeId(Long id) {
		// TODO Auto-generated method stub
		return productsRepo.findByProductTypeId(id);
	}
	@Override
	public void updateQuantity(Integer newQuantity, Long productId) {
		// TODO Auto-generated method stub
		productsRepo.updateQuantity(newQuantity, productId);
	}
	@Override
	public Page<Products> findByProductTypeSlug(String slug,Pageable Page) {
		// TODO Auto-generated method stub
		return productsRepo.findByProductTypeSlug(slug,Page);
	}
	@Override
	public List<Products> findByIsDeletedAndQuantityGreaterThan(Boolean isDeleted, Integer quantity, Pageable page) {
		// TODO Auto-generated method stub
		return productsRepo.findByIsDeletedAndQuantityGreaterThan(isDeleted, quantity, page);
	}
	@Override
	public List<Products> findAll() {
		// TODO Auto-generated method stub
		return productsRepo.findAll();
	}
	@Override
	public Products update(Products product) {
		// TODO Auto-generated method stub
		return productsRepo.saveAndFlush(product);
	}
	@Override
	public List<Products> findByIsDeletedAndQuantityIsLessThanEqual(Boolean isDeleted, Integer quantity) {
		// TODO Auto-generated method stub
		return productsRepo.findByIsDeletedOrQuantityIsLessThanEqual(isDeleted, quantity);
	}

}
