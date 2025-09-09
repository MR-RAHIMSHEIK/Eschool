package com.jts.login.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jts.login.domain.Module;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Integer> {

    @Query("SELECT DISTINCT m FROM User u " +
           "JOIN u.roles r " +
           "JOIN r.modules m " +
           "WHERE u.username = :username")
    Set<Module> findModulesByUsername(@Param("username") String username);
}