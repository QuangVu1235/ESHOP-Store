package com.project.util;

import javax.servlet.http.HttpSession;

import org.springframework.util.ObjectUtils;

import com.project.constant.SessionConst;
import com.project.dto.CartDto;

public class SessionUtil {
	
	public static void validateCart(HttpSession session) {
		if (ObjectUtils.isEmpty(session.getAttribute(SessionConst.CURRENT_CART))) {
			session.setAttribute(SessionConst.CURRENT_CART, new CartDto());
		}
	}
	
	public static CartDto getCurrentCart(HttpSession session) {
		return (CartDto) session.getAttribute(SessionConst.CURRENT_CART);
	}
}
