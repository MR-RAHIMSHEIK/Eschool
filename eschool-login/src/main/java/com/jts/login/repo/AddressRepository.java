package com.jts.login.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jts.login.domain.Address;

/**
 *@author Rahim Sheik
 *@created 01-Sept-2025
 */
public interface AddressRepository extends JpaRepository<Address, Long> {
}
