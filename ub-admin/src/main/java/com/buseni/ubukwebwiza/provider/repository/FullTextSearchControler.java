package com.buseni.ubukwebwiza.provider.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.buseni.ubukwebwiza.provider.domain.Provider;

@RestController
public class FullTextSearchControler {
	@Autowired
	private FullTextSearch ftx;
	
	@RequestMapping(value="/fullTextSearch")
	 // @ResponseBody
	  public Map<Integer, String> fullTextSearch (@RequestParam(value="search") String name) {
	    Map<Integer, String>   providers =  new HashMap<>();
	    List<Provider> results = ftx.fullTextSearch(name);
	    results.stream().forEach(p-> providers.put(p.getId(), p.getBusinessName()));
	    return providers;
	  }

	
	@RequestMapping(value="/refreshIndex")
	//@ResponseBody
	  public void refreshIndex (String name) {
	    
	     ftx.buildIndex();
	  }
}
