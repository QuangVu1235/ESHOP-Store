package com.project.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.ProductDto;
import com.project.entity.ProductTypes;
import com.project.entity.Products;
import com.project.entity.Sale;
import com.project.entity.UnitTypes;
import com.project.repository.ProductTypesRepo;
import com.project.service.ProductService;

@RestController()
@RequestMapping("/api/product")
public class ProductsApi {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ProductTypesRepo repo;
	
	@Autowired
    ModelMapper modelMapper;
	
	@GetMapping("/single")
	public ResponseEntity<?> getProductSingle(@RequestParam("productId") Long productId,Model model) {
		Products product = productService.findById(productId);
		
		return ResponseEntity.ok(product);
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAll() {
		List<Products> product = productService.findAllAvailable();
		
		return ResponseEntity.ok(product);
	}
	
	@GetMapping("/isdelete")
	public ResponseEntity<?> getIsDeleteTrue() {
		List<Products> product = productService.findByIsDeletedAndQuantityIsLessThanEqual(Boolean.TRUE, 0);
		
		return ResponseEntity.ok(product);
	}
	
	
	@PutMapping("{id}/{e}/{d}")
	public Products putUser(@PathVariable("id") String id, @PathVariable("e") String e,@PathVariable("d") String d, @RequestBody Products product) {
		if(product.getQuantity() <= 0 ) {
			product.setIsDeleted(Boolean.TRUE);
		}else {
			ProductTypes types = repo.findById(Long.parseLong(e)).get();
			product.setProductType(types);
			product.setIsDeleted(Boolean.parseBoolean(d));
		}	
		return productService.update(product);
	}
	
	@PutMapping("/{e}/{d}")
	public Products createProduct(@PathVariable("e") String e,@PathVariable("d") String d, @RequestBody ProductDto pro) {
		
		ProductTypes types = repo.findById(Long.parseLong(e)).get();
		Products product = modelMapper.map(pro, Products.class);
		product.setProductType(types);
		product.setIsDeleted(Boolean.parseBoolean(d));
		product.setUnitType(new UnitTypes(1L,"Chiếc", Boolean.FALSE));
		product.setSale(new Sale(4L,0D,""));
		product.setPriceSale(0D);
			
		return productService.update(product);
	}
	
	@PutMapping("remove/{id}")
	public Products remove(@PathVariable("id") String id, @RequestBody Products product) {
			product.setIsDeleted(Boolean.TRUE);
		return productService.update(product);
	}

}
