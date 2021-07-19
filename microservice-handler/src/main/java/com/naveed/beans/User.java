package com.naveed.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {

	private Long id;

	private String username;

	private String fullName;

	private String password;

	private Date create_At;
	private Date update_At;

}
