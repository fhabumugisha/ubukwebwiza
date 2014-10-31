package com.buseni.ubukwebwiza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.buseni.ubukwebwiza.domain.Photo;

public interface PhotoRepo extends JpaRepository<Photo, Integer> {

	@Query("select photo from Photo photo where photo.provide.id =:idProvider")
	List<Photo> findAllByProvider(@Param("idProvider") Integer idProvider);
}
