package com.buseni.ubukwebwiza.provider.predicates;

import com.buseni.ubukwebwiza.provider.beans.ProviderSearch;
import com.buseni.ubukwebwiza.provider.domain.District;
import com.buseni.ubukwebwiza.provider.domain.QProvider;
import com.mysema.query.types.Predicate;

public class ProviderPredicates {
	private static final QProvider PROVIDER = QProvider.provider;

	

	public static Predicate businessNameLike(String  searchTerm){
		return PROVIDER.businessName.like(searchTerm);
	}

	public static Predicate isInDistrict(District  cd){
		return PROVIDER.district.eq(cd);
	}

	/*public static Predicate search(ProviderSearch searchTerm){
		if(searchTerm.getDistrict() != null ){
			if(searchTerm.getService() != null){
				return  PROVIDER.account.enabled.isTrue().and(PROVIDER.district.id.eq(searchTerm.getDistrict()))
						.and(PROVIDER.providerWeddingServices.any().weddingService.id.eq(searchTerm.getService()));
			}else{
				return  PROVIDER.account.enabled.isTrue().and(PROVIDER.district.id.eq(searchTerm.getDistrict()));
			}
		}else if(searchTerm.getService() != null){
			return  PROVIDER.account.enabled.isTrue().and(PROVIDER.providerWeddingServices.any().weddingService.id.eq(searchTerm.getService()));
		}else{
			return  PROVIDER.account.enabled.isTrue();
		}
		
	}*/
	public static Predicate searchByUrlName(ProviderSearch searchTerm){
		if(searchTerm.getDistrict() != null ){
			if(searchTerm.getService() != null){
				return  PROVIDER.account.enabled.isTrue().and(PROVIDER.district.urlName.eq(searchTerm.getDistrict()))
						.and(PROVIDER.providerWeddingServices.any().weddingService.urlName.eq(searchTerm.getService()));
			}else{
				return  PROVIDER.account.enabled.isTrue().and(PROVIDER.district.urlName.eq(searchTerm.getDistrict()));
			}
		}else if(searchTerm.getService() != null){
			return  PROVIDER.account.enabled.isTrue().and(PROVIDER.providerWeddingServices.any().weddingService.urlName.eq(searchTerm.getService()));
		}else{
			return  PROVIDER.account.enabled.isTrue();
		}
		
	}
}
