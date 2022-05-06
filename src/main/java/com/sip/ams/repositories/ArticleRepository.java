package com.sip.ams.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sip.ams.entities.Article;

public interface ArticleRepository extends CrudRepository<Article, Long> {

	@Query("SELECT m FROM Article m WHERE m.label LIKE ?1%")
	List<Article> findBylabel(String label);
}
