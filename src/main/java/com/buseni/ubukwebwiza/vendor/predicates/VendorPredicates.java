package com.buseni.ubukwebwiza.vendor.predicates;

import com.buseni.ubukwebwiza.vendor.domain.CodeDistrict;
import com.buseni.ubukwebwiza.vendor.domain.QVendor;
import com.buseni.ubukwebwiza.vendor.utils.VendorSearch;
import com.mysema.query.types.Predicate;

public class VendorPredicates {
	
	private static final QVendor VENDOR = QVendor.vendor;
	
	

	public static Predicate businessNameLike(String  searchTerm){
		return VENDOR.businessName.like(searchTerm);
	}

	public static Predicate isInDistrict(CodeDistrict  cd){
		return VENDOR.codeDistrict().eq(cd);
	}

	public static Predicate search(VendorSearch searchTerm){	
		return  VENDOR.activeFlag.eq(1).and(VENDOR.codeDistrict().id.eq(searchTerm.getDistrict()))
				.or(VENDOR.weddingServices.any().codeTypeWeddingService().id.eq(searchTerm.getService()));
	}
}
