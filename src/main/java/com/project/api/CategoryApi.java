package com.project.api;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.ProductTypeDto;
import com.project.entity.ProductTypes;
import com.project.service.ProductTypesService;

@RestController
@RequestMapping("/api/category")
public class CategoryApi {
	@Autowired
	ProductTypesService service;
	
	@Autowired
	ModelMapper modelMapper;
	
	@PutMapping("/remove/{id}")
	public ProductTypes remove(@PathVariable("id") String id,@RequestBody ProductTypes types) {
		System.out.println(types.getDescription());
		types.setIsDeleted(Boolean.TRUE);
		return  service.update(types);
	}
	
	@PutMapping("{id}/{d}")
	public ProductTypes putUser(@PathVariable("id") String id,@PathVariable("d") String d, @RequestBody ProductTypes types) {
		types.setIsDeleted(Boolean.parseBoolean(d));
		return service.update(types);
	}
	
	@PutMapping("/{d}")
	public ProductTypes create(@PathVariable("d") String d, @RequestBody ProductTypeDto dto) {
		ProductTypes product = modelMapper.map(dto, ProductTypes.class);
		product.setIsDeleted(Boolean.parseBoolean(d));
		return service.update(product);
	}
}
