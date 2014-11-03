package com.buseni.ubukwebwiza.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buseni.ubukwebwiza.core.domain.CodeWeddingService;
import com.buseni.ubukwebwiza.core.repository.CodeWeddingServiceRepo;
import com.buseni.ubukwebwiza.core.service.CodeWeddingServiceManager;

@Service("codeWeddingServiceManager")
@Transactional(readOnly=true)
public class CodeWeddingServiceManagerImpl implements CodeWeddingServiceManager {

	
	private CodeWeddingServiceRepo codeWeddingServiceRepo;
	
	public CodeWeddingServiceManagerImpl(){
		
	}
	@Autowired
	public CodeWeddingServiceManagerImpl(CodeWeddingServiceRepo codeWeddingServiceRepo) {
		this.codeWeddingServiceRepo = codeWeddingServiceRepo;
	}
	@Override
	public List<CodeWeddingService> findAll() {
		return codeWeddingServiceRepo.findAll();
	}

	@Override
	public Page<CodeWeddingService> findAll(Pageable pageable) {
		return codeWeddingServiceRepo.findAll(pageable);
	}
	@Override
	public List<CodeWeddingService> findByActiveFlag(int activeFlag) {
		return codeWeddingServiceRepo.findByActiveFlag(activeFlag);
	}

}
