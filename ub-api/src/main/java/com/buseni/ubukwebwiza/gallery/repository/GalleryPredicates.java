package com.buseni.ubukwebwiza.gallery.repository;

import com.buseni.ubukwebwiza.administrator.enums.EnumPhotoCategory;
import com.buseni.ubukwebwiza.gallery.domain.QPhoto;
import com.querydsl.core.types.Predicate;


public class GalleryPredicates {
	private static final QPhoto PHOTO = QPhoto.photo;

	public static Predicate galleryPhotos() {
		return PHOTO.enabled.isTrue().and(PHOTO.isGalleryPhoto.isTrue()).and(PHOTO.category.eq(EnumPhotoCategory.PROVIDER.getId()));

	}
	
	public static Predicate homePagePhotos() {
		return PHOTO.enabled.isTrue().and(PHOTO.category.eq(EnumPhotoCategory.HOME_PAGE.getId()));

	}
}
