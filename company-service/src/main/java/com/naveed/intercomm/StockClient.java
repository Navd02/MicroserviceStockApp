package com.naveed.intercomm;


import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naveed.beans.Company;

@FeignClient(name="stock-service" ,url="http://localhost:8100")
@RibbonClient(name="stock-service")


public interface StockClient {
	@RequestMapping(method = RequestMethod.DELETE, value = "/api/v1.0/market/stock/deleteStock/{companyCode}")
    void deleteStock(@PathVariable("companyCode") String companyCode);
    

}




