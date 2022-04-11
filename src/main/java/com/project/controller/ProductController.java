package com.project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.entity.Products;
import com.project.service.ProductService;

@Controller
@RequestMapping("/product/")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
//	@RequestParam("productId") Long productId
	@GetMapping("/single/{id}")
	public String getSingle(Model model,@PathVariable("id") Long id) {
		Products product = productService.findById(id);
		List<Products> products = productService.findByTypeId(product.getProductType().getId());
		model.addAttribute("product",product);
		model.addAttribute("products",products);
		return "user/single";
	}
	
	@GetMapping("/slug")
	public String getCategory(Model model, @RequestParam("slug") String slug, @RequestParam("p") Optional<Integer> p) {
		Pageable pageable = PageRequest.of(p.orElse(0), 6);
		Page<Products> products = productService.findByProductTypeSlug(slug,pageable);
		model.addAttribute("products",products);
		model.addAttribute("slug",slug);
		return "user/shop-list";
	}
	
	@GetMapping("/search")
	public String getSearch(Model model, @RequestParam("name") String name, @RequestParam("p") Optional<Integer> p) {
		Pageable pageable = PageRequest.of(p.orElse(0), 6);
		Page<Products> products = productService.findByNameLike(name,pageable);
		model.addAttribute("products",products);
		return "user/shop-list";
	}

	
}
