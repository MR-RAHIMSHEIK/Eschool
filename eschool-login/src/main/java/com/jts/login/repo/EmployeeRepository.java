package com.jts.login.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jts.login.domain.Employee;

/**
 *@author Rahim Sheik
 *@created 07-Sept-2025
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	Optional<Employee> findByEmployeeCodeIgnoreCase(String employeeCode);
	
	Optional<Employee> findByEmployeeNameIgnoreCase(String employeeCode);
		
    List<Employee> findByEmployeeCodeContainingIgnoreCase(String employeeCode);

    List<Employee> findByEmployeeNameContainingIgnoreCase(String employeeName);
	
	}
