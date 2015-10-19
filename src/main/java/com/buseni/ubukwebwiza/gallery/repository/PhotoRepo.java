package com.buseni.ubukwebwiza.gallery.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.buseni.ubukwebwiza.gallery.beans.PhotoDetails;
import com.buseni.ubukwebwiza.gallery.domain.Photo;

public interface PhotoRepo extends JpaRepository<Photo, Integer> , QueryDslPredicateExecutor<Photo>{

	Page<Photo> findByEnabled(boolean enabled, Pageable pageable);
	
	Page<Photo> findByCategory(Integer category, Pageable pageable);
	
	Page<Photo> findByEnabledAndCategory(boolean enabled, Integer category, Pageable pageable);
	
	@Query("select new com.buseni.ubukwebwiza.gallery.beans.PhotoDetails(photo.id, photo.description, photo.filename, provider.id, provider.businessName, provider.urlName)"
			+ " from Provider provider join provider.photos photo where photo.enabled=true  and photo.isGalleryPhoto=true and photo.category=1 "
			+ " order by photo.lastUpdate desc")
	Page<PhotoDetails>  findPhotoGallery( Pageable pageable);
	
}
