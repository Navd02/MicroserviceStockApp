package com.naveed.controller;


import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naveed.beans.Company;
import com.naveed.repository.CompanyRepository;
import com.naveed.service.MapValidationErrorService;
import com.naveed.exceptions.CompanyCodeException;
import com.naveed.intercomm.StockClient;

@RestController
@RequestMapping("/company")

public class CompanyController {
	
	Logger logger = LoggerFactory.getLogger(CompanyController.class);
	
	@Autowired
	CompanyRepository companyRepo;
	
	@Autowired
	MapValidationErrorService mapValidationErrorService;
	
	@Autowired
	StockClient stockClient;

	@PostMapping("/register")
	public ResponseEntity<?> newRegistration(@Valid @RequestBody Company registration , BindingResult result) {
		logger.info("The request body : {}" , registration );
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap!=null) {
        	logger.error("Error in ValidationService : {}", errorMap);
        	return errorMap;
        }
        Company company = new Company();
		try {
		registration.setCompanyCode(registration.getCompanyCode().toUpperCase());
		 company = companyRepo.save(registration);
		 logger.info("The company with id :"  + company.getCompanyCode() + " is registered");
		
		}catch(Exception e) {
			Long id = registration.getId();
			Optional<Company> findByIdCompany = companyRepo.findById(id);
			if(findByIdCompany.isPresent()) {
				Company dbCompany = findByIdCompany.get();
				if(dbCompany != null) {
					dbCompany.setLatestStockPrice(registration.getLatestStockPrice());
					logger.info("The latest stock price for the company is : " + registration.getLatestStockPrice() );
					companyRepo.save(dbCompany);
					
				}else {
					logger.error("Project Id - " + registration.getCompanyCode() + " Already Exists");
					throw new CompanyCodeException("Project Id Already Exists");
				}
				
			}else {
				logger.error("Project Id - " + registration.getCompanyCode() + " Already Exists");
			throw new CompanyCodeException("Project Id Already Exists");
			}
		}
		return new ResponseEntity<Company>(company, HttpStatus.CREATED);
		
	}
	
	
	@GetMapping("/info/{companyCode}")
	public ResponseEntity<?> getCompanyDetails(@PathVariable String companyCode){
		
		Company company = companyRepo.findByCompanyCode(companyCode);
		
		if(company == null) {
			logger.error("Company with code " + companyCode + " doesnt exist");
			throw new CompanyCodeException("Company with code " + companyCode + " doesnt exist" );
		}
		
		return new ResponseEntity<Company>(company , HttpStatus.OK);
	}
	
	//Internal Method that exactly functions as the getCompanyDetails method. 
	//but is used with feign so no authentication is required for accessing below method.
	// this end point is not exposed to the outside world.
	@GetMapping("/getCompany/{companyCode}")
	public ResponseEntity<?> getCompanyInfo(@PathVariable String companyCode){
		
		Company company = companyRepo.findByCompanyCode(companyCode);
		
		if(company == null) {
			logger.error("Company with code " + companyCode + " doesnt exist");
			throw new CompanyCodeException("Company with code " + companyCode + " doesnt exist" );
		}
		
		return new ResponseEntity<Company>(company , HttpStatus.OK);
	}
	
	@GetMapping("/getall")
	public List<Company> getAllCompanies(){
		
		List<Company> companyList =  companyRepo.findAll();
		return companyList;
	}
	

	
	@DeleteMapping("/delete/{companyCode}")
	public ResponseEntity<?> deleteCompany(@PathVariable String companyCode){
		Company company = companyRepo.findByCompanyCode(companyCode);
		
		if(company == null) {
			logger.error("Company with code " + companyCode + " doesnt exist");
			throw new CompanyCodeException("Company with code " + companyCode + " doesnt exist" );
		}
		stockClient.deleteStock(companyCode);
		companyRepo.delete(company);
		
		return new ResponseEntity<String>("Company with Code: '"+companyCode+"' was deleted", HttpStatus.OK);
		
	}
}
