package com.naveed.repository;

import org.springframework.data.repository.CrudRepository;

import com.naveed.beans.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByUsername(String username);
    User getById(Long id);

}
