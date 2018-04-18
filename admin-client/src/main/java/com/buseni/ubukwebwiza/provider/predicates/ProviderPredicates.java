package com.buseni.ubukwebwiza.provider.predicates;

import org.apache.commons.lang3.StringUtils;

import com.buseni.ubukwebwiza.provider.beans.ProviderSearch;
import com.buseni.ubukwebwiza.provider.domain.District;
import com.buseni.ubukwebwiza.provider.domain.QProvider;
import com.querydsl.core.types.Predicate;

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
		if(StringUtils.isNotEmpty(searchTerm.getDistrict()) && !ProviderSearch.ALL_DISTRICTS.equals(searchTerm.getDistrict())){
			if(StringUtils.isNotEmpty(searchTerm.getService())  && !ProviderSearch.ALL_SERVICES.equals(searchTerm.getService())){
				return  PROVIDER.account.enabled.isTrue().and(PROVIDER.district.urlName.eq(searchTerm.getDistrict()))
						.and(PROVIDER.providerWeddingServices.any().weddingService.urlName.eq(searchTerm.getService()));
			}else{
				return  PROVIDER.account.enabled.isTrue().and(PROVIDER.district.urlName.eq(searchTerm.getDistrict()));
			}
		}else if(StringUtils.isNotEmpty(searchTerm.getService()) && !ProviderSearch.ALL_SERVICES.equals(searchTerm.getService())){
			return  PROVIDER.account.enabled.isTrue().and(PROVIDER.providerWeddingServices.any().weddingService.urlName.eq(searchTerm.getService()));
		}else{
			return  PROVIDER.account.enabled.isTrue();
		}
		
	}
}
