package com.project.service.impl;

import java.util.HashMap;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dto.CartDetailDto;
import com.project.dto.CartDto;
import com.project.entity.Orders;
import com.project.entity.Products;
import com.project.entity.Users;
import com.project.service.CartService;
import com.project.service.OrderDetailService;
import com.project.service.OrderService;
import com.project.service.ProductService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderDetailService orderDetailService;

	@Override
	public CartDto updateCartnew(CartDto cart, Long productId, Integer quantity, boolean isReplace) {
		// TODO Auto-generated method stub
		// tim san pham
		Products product = productService.findById(productId);

		HashMap<Long, CartDetailDto> listDetail = cart.getListDetail();

		if (!listDetail.containsKey(productId)) {
			// them moi
			CartDetailDto cartDetail = addNewCartDetail(product, quantity);
			listDetail.put(productId, cartDetail);
		} else if (quantity > 0) {
			// update
			if (isReplace) {
				// thay the SL cu = SL moi
				listDetail.get(productId).setQuantity(quantity);
			} else {
				// cong don
				Integer oldQuantity = listDetail.get(productId).getQuantity();
				Integer newQuantity = oldQuantity + quantity;
				listDetail.get(productId).setQuantity(newQuantity);
			}
		} else {
			// delete
			listDetail.remove(productId);
		}
		cart.setTotalQuantity(getTotalQuantity(cart));
		cart.setTotalPrice(getTotalPrice(cart));
		return cart;
	}

	@Override
	public Integer getTotalQuantity(CartDto cart) {
		// TODO Auto-generated method stub
		Integer totalQTT = 0;
		HashMap<Long, CartDetailDto> list = cart.getListDetail();
		for (CartDetailDto detail : list.values()) {
			totalQTT += detail.getQuantity();
		}
		return totalQTT;
	}

	@Override
	public Double getTotalPrice(CartDto cart) {
		// TODO Auto-generated method stub
		Double totalPrice = 0D;
		HashMap<Long, CartDetailDto> list = cart.getListDetail();
		for (CartDetailDto detail : list.values()) {
			totalPrice += detail.getQuantity() * detail.getPrice();
		}
		return totalPrice;
	}

	private CartDetailDto addNewCartDetail(Products product, Integer quantity) {
		CartDetailDto cartDetail = new CartDetailDto();
		cartDetail.setProductId(product.getId());
		cartDetail.setQuantity(quantity);
		cartDetail.setPrice(product.getPrice());
		cartDetail.setName(product.getName());
		cartDetail.setImgUrl(product.getImgUrl());
		cartDetail.setSlug(product.getSlug());
		return cartDetail;
	}

	@Transactional
	@Override
	public void insert(CartDto cart, Users user, String address, String phone) throws Exception {
			Orders order = new Orders();
			order.setAddress(address);
			order.setPhone(phone);
			order.setUser(user);
			order.setIsActive(Boolean.FALSE);
			try {
				Orders orderResponse = orderService.insert(order);

				for (CartDetailDto cartDetail : cart.getListDetail().values()) {
					// insert ORDER_DETAILS:
					cartDetail.setOrderId(orderResponse.getId());
					orderDetailService.insert(cartDetail);

					// update quantity for PRODUCTS
					Products product = productService.findById(cartDetail.getProductId());
					Integer newQuantity = product.getQuantity() - cartDetail.getQuantity();
					productService.updateQuantity(newQuantity, product.getId());
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new Exception("cannot insert cart to DB");
			}
		}
	

}
