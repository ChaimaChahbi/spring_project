package com.sip.ams.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sip.ams.entities.User;

public interface UserRrepository extends CrudRepository<User, Long>{
	@Query("SELECT m FROM User m WHERE m.login LIKE ?1%")
	User findByUsername(String username);
}
