package com.buseni.ubukwebwiza.core.service;

import java.util.List;

import com.buseni.ubukwebwiza.core.domain.CodeDistrict;

public interface CodeDistrictService {
	
	List<CodeDistrict> findByActiveFlag(int activeFlag);

}
