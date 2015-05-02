package com.buseni.ubukwebwiza.gallery.repository;

import com.buseni.ubukwebwiza.administrator.enums.EnumPhotoCategory;
import com.buseni.ubukwebwiza.gallery.domain.QPhoto;
import com.mysema.query.types.Predicate;

public class GalleryPredicates {
	private static final QPhoto PHOTO = QPhoto.photo;

	public static Predicate gelleryPhotos() {
		return PHOTO.enabled.isTrue().and(
				PHOTO.category.eq(EnumPhotoCategory.PROVIDER.getId()));

	}
}
