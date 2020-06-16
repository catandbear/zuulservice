package com.fsd2020.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fsd2020.feignservice.SecurityConnection;

@Service
public class TokenValidation {

	private SecurityConnection securityConn;
	Logger logger = LoggerFactory.getLogger(TokenValidation.class);
	
	@Autowired
	private TokenValidation(SecurityConnection securityConn) {
		this.securityConn = securityConn;
	}
	
	public boolean validateToken(String token) {
		logger.warn("start send token");
		return securityConn.query(token);
	}
}
