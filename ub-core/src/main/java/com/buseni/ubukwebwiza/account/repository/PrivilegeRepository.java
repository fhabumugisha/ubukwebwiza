package com.buseni.ubukwebwiza.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ubukwebwiza.account.domain.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {
     Privilege findByName(String name);

     void delete(Privilege privilege);
}