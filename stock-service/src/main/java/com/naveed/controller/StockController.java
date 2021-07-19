package com.naveed.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naveed.beans.Company;
//import com.naveed.beans.Company;
import com.naveed.beans.FinalStockList;
import com.naveed.beans.Stock;
import com.naveed.exceptions.StockException;
import com.naveed.intercomm.CompanyClient;
//import com.naveed.repository.CompanyRepository;
import com.naveed.repository.StockRepository;
import com.naveed.service.MapValidationErrorService;

import feign.FeignException;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/stock")

public class StockController {
	
	@Autowired
	MapValidationErrorService mapValidationErrorService;
	
	@Autowired
	StockRepository stockRepo;
	
	@Autowired
	CompanyClient companyClient;
	
//	@Autowired
//	CompanyRepository companyRepo;
	
	@PostMapping("/add/{companyCode}")
	@ApiOperation(value = "Adds Stock to a Company", notes = "Add Stock to a company Code and this api will persist that to the database" , 
	response = Stock.class )
	public ResponseEntity<?> addStock(@Valid @RequestBody Stock stock, @PathVariable String companyCode, BindingResult result){
		
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap!=null) return errorMap;
		
		Company company = companyClient.getCompany(companyCode);
		Stock receivedStock;
		if(company == null) {
			throw new StockException("Company with code " + companyCode + " doesnt exist" );
		}else {
			
			stock.setCompanyCode(companyCode);
			company.setLatestStockPrice(stock.getStockPrice());
			
			receivedStock = stockRepo.save(stock);
			try {
				companyClient.postLatestStock(company);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return new ResponseEntity<Stock>(receivedStock, HttpStatus.CREATED);
			}
			
		}
		return new ResponseEntity<Stock>(receivedStock, HttpStatus.CREATED);
	}
	
	@GetMapping("/get/{companyCode}/{startDate}/{endDate}")
	public ResponseEntity<?> getStockInRange(@PathVariable String companyCode, @PathVariable String startDate , @PathVariable String endDate){
		LocalDate startDate1 = LocalDate.parse(startDate);
		LocalDate endDate1 = LocalDate.parse(endDate);
		
		List<Stock> stockList = stockRepo.getByCompanyCodeAndCreateAtBetween(companyCode, startDate1, endDate1);
		if(stockList == null || stockList.isEmpty()) {
			throw new StockException("No Data found in  " + startDate1 + "-" + endDate1 +" range" );
		}
		//This method calculates the max, min, avg stockprices
		FinalStockList finalList = new FinalStockList();
		calculateStockPrices(stockList , finalList);
		
		return new ResponseEntity<FinalStockList>(finalList , HttpStatus.OK); 
	}
	
	@DeleteMapping("/deleteStock/{companyCode}")
	public void deleteStock(@PathVariable String companyCode){
		
		List<Stock> stocklist = stockRepo.getByCompanyCode(companyCode); 
		stockRepo.deleteInBatch(stocklist);
		
		
		
	}
	
	
	
	private void calculateStockPrices(List<Stock> stockList, FinalStockList finalList) {
		List<Double> stockPrices = new ArrayList<>();
		for(Stock s : stockList) {
			stockPrices.add(s.getStockPrice());
		}
		
	if(!stockPrices.isEmpty()) {
		Collections.sort(stockPrices);
		Double min = stockPrices.get(0);
		Double max = stockPrices.get(stockPrices.size()-1);
		Double avg = ((max + min)/2);
		
		finalList.setStocks(stockList);
		finalList.setMinStockPrice(min);
		finalList.setMaxStockPrice(max);
		finalList.setAvgStockPrice(avg);
		
	}
	
		
	}

	

}
