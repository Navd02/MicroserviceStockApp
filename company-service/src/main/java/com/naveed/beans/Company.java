package com.naveed.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message="Company Code is Required")
	@Column(updatable = false, unique = true)
	@Size(min=4, max=5, message = "Please use 4 to 5 characters")
	private String companyCode;
	
	@NotBlank(message="Company Name is Required")
	private String companyName;
	
	@NotBlank(message="Company CEO is Required")
	private String companyCEO;
	
	@NotNull(message="Company Turnover is Required")
	@Range(min=1_00_00_000, message="TurnOver should be greater than 10 Crore")
	private long companyTurnover;
	
	@NotBlank(message="Company Website is Required")
	@Pattern(regexp = "^(https?:\\/\\/)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?$" , message="This field should be a website. Ex : https://www.google.com")
	private String companyWebsite;
	
	@NotBlank(message="Stock Exchange is Required")
	private String stockExchange;
	
	private Double latestStockPrice;
	

}
