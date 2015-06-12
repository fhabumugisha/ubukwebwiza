package com.buseni.ubukwebwiza.gallery.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.buseni.ubukwebwiza.gallery.domain.Photo;
import com.buseni.ubukwebwiza.gallery.domain.PhotoDetails;

public interface PhotoRepo extends JpaRepository<Photo, Integer> , QueryDslPredicateExecutor<Photo>{

	Page<Photo> findByEnabled(boolean enabled, Pageable pageable);
	
	Page<Photo> findByCategory(Integer category, Pageable pageable);
	
	Page<Photo> findByEnabledAndCategory(boolean enabled, Integer category, Pageable pageable);
	
	/*@Query("select new com.buseni.ubukwebwiza.gallery.domain.PhotoDetails(p.id, p.description) from Photo p where p.enabled=true and p.category=:category")
	Page<PhotoDetails>  findPhotoDetails(@Param("category")Integer category, Pageable pageable);*/
	
}
