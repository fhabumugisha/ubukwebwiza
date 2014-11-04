package com.buseni.ubukwebwiza.core.service.impl;

import java.util.ArrayList;
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
	
	/*public CodeWeddingServiceManagerImpl(){
		
	}*/
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
		
		List<CodeWeddingService> weddingServices =   new ArrayList<CodeWeddingService>();
		CodeWeddingService ws = new CodeWeddingService();
		ws.setId(1);
		ws.setLibelle("Wedding planner");
		weddingServices.add(ws);
		
		ws = new CodeWeddingService();
		ws.setId(2);
		ws.setLibelle("Catering");
		weddingServices.add(ws);
		
		ws = new CodeWeddingService();
		ws.setId(3);
		ws.setLibelle("Car");
		weddingServices.add(ws);
		//return codeWeddingServiceRepo.findByActiveFlag(activeFlag);
		return weddingServices;
	}

}
