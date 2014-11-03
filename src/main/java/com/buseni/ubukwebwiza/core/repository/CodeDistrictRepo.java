package com.buseni.ubukwebwiza.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.buseni.ubukwebwiza.core.domain.CodeDistrict;

public interface CodeDistrictRepo  extends JpaRepository<CodeDistrict, Integer>{

	@Query("select cd  from CodeDistrict cd where dc.activeFlag =:activeFlag group by cd.codeProvice.id")
	List<CodeDistrict> findByActiveFlagGrouped(@Param("activeFlag") int activeFlag);
}
