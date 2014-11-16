package com.buseni.ubukwebwiza.vendor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buseni.ubukwebwiza.vendor.domain.CodeTypeWeddingService;
import com.buseni.ubukwebwiza.vendor.repository.CodeTypeWeddingServiceRepo;
import com.buseni.ubukwebwiza.vendor.service.CodeWeddingServiceManager;

@Service("codeTypeWeddingServiceManager")
@Transactional(readOnly=true)
public class CodeTypeWeddingServiceManagerImpl implements CodeWeddingServiceManager {

	
	private CodeTypeWeddingServiceRepo codeTypeWeddingServiceRepo;
	
	/*public CodeTypeWeddingServiceManagerImpl(){
		
	}*/
	@Autowired
	public CodeTypeWeddingServiceManagerImpl(CodeTypeWeddingServiceRepo codeTypeWeddingServiceRepo) {
		this.codeTypeWeddingServiceRepo = codeTypeWeddingServiceRepo;
	}
	@Override
	public List<CodeTypeWeddingService> findAll() {
		return codeTypeWeddingServiceRepo.findAll();
	}

	@Override
	public Page<CodeTypeWeddingService> findAll(Pageable pageable) {
		return codeTypeWeddingServiceRepo.findAll(pageable);
	}
	@Override
	public List<CodeTypeWeddingService> findByActiveFlag(int activeFlag) {
		
		return codeTypeWeddingServiceRepo.findByActiveFlag(activeFlag);
		
	}

}
