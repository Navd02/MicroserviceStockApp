package com.naveed.beans;

import java.time.LocalDate;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.Digits;

import javax.validation.constraints.NotNull;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull(message = "Stock Price Cannot be null")
	@Digits(integer= 9, fraction=9 , message="Stock Price should be a fraction")
	private Double stockPrice;
	
	
	@Column(updatable = false)
	@JsonIgnore
	private String companyCode;
	
	private LocalDate createAt;
	
	
	@PrePersist
    protected void onCreate(){
        this.createAt = LocalDate.now();
    }
}
