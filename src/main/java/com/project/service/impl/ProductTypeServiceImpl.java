package com.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.entity.ProductTypes;
import com.project.repository.ProductTypesRepo;
import com.project.service.ProductTypesService;

@Service
public class ProductTypeServiceImpl implements ProductTypesService {

    @Autowired
    ProductTypesRepo repo;
    
    @Override
    public List<ProductTypes> findAll() {
        return repo.findAll();
    }
    
	@Override
	public ProductTypes update(ProductTypes types) {
		// TODO Auto-generated method stub
		return repo.saveAndFlush(types) ;
	}
}
