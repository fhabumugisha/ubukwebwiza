package com.buseni.ubukwebwiza.gallery.repository;

import org.springframework.util.Assert;

import com.buseni.ubukwebwiza.gallery.domain.QPhoto;
import com.mysema.query.types.Predicate;

public class GalleryPredicates {
	private static final QPhoto PHOTO = QPhoto.photo;

	public static Predicate gelleryPhotos(Integer category) {
		Assert.notNull(category, "The given id must not be null!");
		
		return PHOTO.enabled.isTrue().and(PHOTO.category.eq(category));

	}
}
