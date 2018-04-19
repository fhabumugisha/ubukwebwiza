package com.buseni.ubukwebwiza.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ubukwebwiza.account.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
     Role findByName(String name);

     void delete(Role role);
}