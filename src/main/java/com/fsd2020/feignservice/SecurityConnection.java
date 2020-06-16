package com.fsd2020.feignservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "securityservice")
public interface SecurityConnection {
	@GetMapping("querytoken")
	public boolean query(@RequestParam(value="token") String token);
}
