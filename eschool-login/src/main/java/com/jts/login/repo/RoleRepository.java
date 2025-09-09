package com.jts.login.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jts.login.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

}
