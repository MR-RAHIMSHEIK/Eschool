package com.jts.login.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jts.login.domain.Branch;
import com.jts.login.domain.Organization;
import com.jts.login.dto.BranchRequestDTO;
import com.jts.login.dto.BranchResponseDTO;
import com.jts.login.dto.OrganizationRequestDTO;
import com.jts.login.dto.OrganizationResponseDTO;
import com.jts.login.service.OrganizationService;

import java.util.List;

/**
 *@author Rahim Sheik
 *@created 01-Sept-2025
 */

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {

	private final OrganizationService organizationService;

	@PostMapping
	public ResponseEntity<OrganizationResponseDTO> createOrganization(
			@RequestBody OrganizationRequestDTO dto) {
		return ResponseEntity.ok(organizationService.saveOrganization(dto));
	}

	@PostMapping("/{orgId}/branches")
	public ResponseEntity<BranchResponseDTO> createBranch(
			@PathVariable Long orgId, @RequestBody BranchRequestDTO dto) {
		return ResponseEntity.ok(organizationService.saveBranch(orgId, dto));
	}

	@GetMapping
	public ResponseEntity<List<OrganizationResponseDTO>> getOrganizations() {
		return ResponseEntity.ok(organizationService.getAllOrganizations());
	}

	@GetMapping("/{orgId}/branches")
	public ResponseEntity<List<BranchResponseDTO>> getBranches(@PathVariable Long orgId) {
		return ResponseEntity.ok(organizationService.getBranchesByOrganization(orgId));
	}

	@PutMapping("/{orgId}")
	public ResponseEntity<OrganizationResponseDTO> updateOrganization(
			@PathVariable Long orgId, @RequestBody OrganizationRequestDTO dto) {
		return ResponseEntity.ok(organizationService.updateOrganization(orgId, dto));
	}

	@PutMapping("/{orgId}/branches/{branchId}")
	public ResponseEntity<BranchResponseDTO> updateBranch(
			@PathVariable Long orgId,
			@PathVariable Long branchId,
			@RequestBody BranchRequestDTO dto) {
		return ResponseEntity.ok(organizationService.updateBranch(orgId, branchId, dto));
	}

	@DeleteMapping("/{orgId}")
	public ResponseEntity<Void> deleteOrganization(@PathVariable Long orgId) {
		organizationService.deleteOrganization(orgId);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{orgId}/branches/{branchId}")
	public ResponseEntity<Void> deleteBranch(
			@PathVariable Long orgId, @PathVariable Long branchId) {
		organizationService.deleteBranch(orgId, branchId);
		return ResponseEntity.noContent().build();
	}
}
