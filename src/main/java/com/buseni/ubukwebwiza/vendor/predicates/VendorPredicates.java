package com.buseni.ubukwebwiza.vendor.predicates;

import com.buseni.ubukwebwiza.vendor.domain.District;
import com.buseni.ubukwebwiza.vendor.domain.QVendor;
import com.buseni.ubukwebwiza.vendor.utils.VendorSearch;
import com.mysema.query.types.Predicate;

public class VendorPredicates {
	
	private static final QVendor VENDOR = QVendor.vendor;
	
	

	public static Predicate businessNameLike(String  searchTerm){
		return VENDOR.businessName.like(searchTerm);
	}

	public static Predicate isInDistrict(District  cd){
		return VENDOR.district().eq(cd);
	}

	public static Predicate search(VendorSearch searchTerm){	
		return  VENDOR.enabled.isTrue().and(VENDOR.district().id.eq(searchTerm.getDistrict()))
				.or(VENDOR.vendorWeddingServices.any().weddingService().id.eq(searchTerm.getService()));
	}
}
