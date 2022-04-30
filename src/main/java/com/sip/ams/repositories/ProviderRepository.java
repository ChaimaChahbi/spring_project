package com.sip.ams.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.sip.ams.entities.Provider;
import com.sip.ams.entities.User;

@Repository
public interface ProviderRepository extends CrudRepository<Provider, Long> {
	@Query("SELECT m FROM Provider m WHERE m.name LIKE ?1%")
	User findByProvidername(String name);

}
