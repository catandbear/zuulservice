package com.fsd2020.filter;

import org.springframework.stereotype.Component;

@Component
public class TokenValidation {

	public void validateToken(String token) {
		System.out.println("验证token : " + token);
	}
}
