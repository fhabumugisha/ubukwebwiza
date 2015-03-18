package com.buseni.ubukwebwiza.vendor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ubukwebwiza.vendor.domain.Photo;

public interface PhotoRepo extends JpaRepository<Photo, Integer> {

	Page<Photo> findByEnabled(boolean enabled, Pageable pageable);
}
