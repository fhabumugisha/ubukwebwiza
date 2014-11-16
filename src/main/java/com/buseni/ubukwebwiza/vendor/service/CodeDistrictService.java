package com.buseni.ubukwebwiza.vendor.service;

import java.util.List;

import com.buseni.ubukwebwiza.vendor.domain.CodeDistrict;

public interface CodeDistrictService {
	
	List<CodeDistrict> findByActiveFlag(int activeFlag);

}
