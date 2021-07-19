package com.naveed.intercomm;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naveed.beans.User;

@FeignClient(name="login-service" ,url="http://localhost:8100")
@RibbonClient(name="login-service")
public interface LoginClient {

	@RequestMapping(method = RequestMethod.GET, value = "/api/v1.0/market/users/getUserById/{id}")
    User getUserById(@PathVariable("id") Long id);
}







