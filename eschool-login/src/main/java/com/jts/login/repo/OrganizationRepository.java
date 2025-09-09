package com.jts.login.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jts.login.domain.Organization;

/**
 *@author Rahim Sheik
 *@created 01-Sept-2025
 */
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}