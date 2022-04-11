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
public class ProductsServiceImpl implements ProductService {

	@Autowired
	ProductsRepo productsRepo;

	@Override
	public List<Products> findAllAvailable() {

		return productsRepo.findByIsDeletedAndQuantityGreaterThan(Boolean.FALSE, 0);
	}

	@Override
	public Products findById(Long productId) {

		Optional<Products> optional = productsRepo.findById(productId);
		return optional.isPresent() ? optional.get() : null;

	}

	@Override
	public List<Products> findByTypeId(Long id) {

		return productsRepo.findByProductTypeId(id);
	}

	@Override
	public void updateQuantity(Integer newQuantity, Long productId) {

		productsRepo.updateQuantity(newQuantity, productId);
	}

	@Override
	public Page<Products> findByProductTypeSlug(String slug, Pageable Page) {

		return productsRepo.findByProductTypeSlug(slug, Page);
	}

	@Override
	public List<Products> findByIsDeletedAndQuantityGreaterThan(Boolean isDeleted, Integer quantity, Pageable page) {
		return productsRepo.findByIsDeletedAndQuantityGreaterThan(isDeleted, quantity, page);
	}

	@Override
	public List<Products> findAll() {
		return productsRepo.findAll();
	}

	@Override
	public Products update(Products product) {
		return productsRepo.saveAndFlush(product);
	}

	@Override
	public List<Products> findByIsDeletedAndQuantityIsLessThanEqual(Boolean isDeleted, Integer quantity) {
		return productsRepo.findByIsDeletedOrQuantityIsLessThanEqual(isDeleted, quantity);
	}

	@Override
	public Page<Products> findByNameLike(String name, Pageable pageable) {

		return productsRepo.findByNameIsLike("%"+name+"%", pageable);
	}

}
