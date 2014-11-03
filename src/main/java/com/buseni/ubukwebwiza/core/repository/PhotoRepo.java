package com.buseni.ubukwebwiza.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ubukwebwiza.core.domain.Photo;

public interface PhotoRepo extends JpaRepository<Photo, Integer> {

	List<Photo> findByProvider_id(Integer idProvider);
}
