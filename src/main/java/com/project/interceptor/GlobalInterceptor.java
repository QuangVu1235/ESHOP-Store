package com.project.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.project.entity.ProductTypes;
import com.project.service.ProductService;
import com.project.service.ProductTypesService;
import com.project.service.UsersService;

@Service
public class GlobalInterceptor implements HandlerInterceptor {
    
	@Autowired
	UsersService usersService;
	
	@Autowired
	ProductService productService;

	@Autowired
	ProductTypesService productTypesService;
    
    @Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		request.setAttribute("uri", request.getRequestURI());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
		List<ProductTypes> types = productTypesService.findAll();
		request.setAttribute("productstype", types);
		
	}
}
