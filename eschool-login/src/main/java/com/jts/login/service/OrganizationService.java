package com.jts.login.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jts.login.domain.Branch;
import com.jts.login.domain.Organization;
import com.jts.login.dto.BranchRequestDTO;
import com.jts.login.dto.BranchResponseDTO;
import com.jts.login.dto.OrganizationRequestDTO;
import com.jts.login.dto.OrganizationResponseDTO;
import com.jts.login.mapper.OrganizationMapper;
import com.jts.login.repo.BranchRepository;
import com.jts.login.repo.OrganizationRepository;

import lombok.RequiredArgsConstructor;

/**
 *@author Rahim Sheik
 *@created 01-Sept-2025
 */
@Service
@RequiredArgsConstructor
public class OrganizationService {

	private final OrganizationRepository organizationRepository;
	private final BranchRepository branchRepository;

	public OrganizationResponseDTO saveOrganization(OrganizationRequestDTO dto) {
		Organization org = OrganizationMapper.toEntity(dto);
		Organization saved = organizationRepository.save(org);
		return OrganizationMapper.toDto(saved);
	}

	public BranchResponseDTO saveBranch(Long organizationId, BranchRequestDTO dto) {
		Organization org = organizationRepository.findById(organizationId)
				.orElseThrow(() -> new RuntimeException("Organization not found"));
		Branch branch = OrganizationMapper.toEntity(dto, org);
		Branch saved = branchRepository.save(branch);
		return OrganizationMapper.toBranchDto(saved);
	}

	public List<OrganizationResponseDTO> getAllOrganizations() {
		return organizationRepository.findAll()
				.stream()
				.map(OrganizationMapper::toDto)
				.toList();
	}

	public List<BranchResponseDTO> getBranchesByOrganization(Long orgId) {
		return branchRepository.findAll().stream()
				.filter(b -> b.getOrganization().getId().equals(orgId))
				.map(OrganizationMapper::toBranchDto)
				.toList();
	}
	public OrganizationResponseDTO updateOrganization(Long orgId, OrganizationRequestDTO dto) {
		Organization org = organizationRepository.findById(orgId)
				.orElseThrow(() -> new RuntimeException("Organization not found"));
		org.setOrganizationCode(dto.getOrganizationCode());
		org.setOrganizationName(dto.getOrganizationName());
		org.setOrganizationContact(dto.getOrganizationContact());
		org.setOrganizationAddress(OrganizationMapper.toEntity(dto.getOrganizationAddress()));
		Organization updated = organizationRepository.save(org);
		return OrganizationMapper.toDto(updated);
	}

	public BranchResponseDTO updateBranch(Long orgId, Long branchId, BranchRequestDTO dto) {
		Branch branch = branchRepository.findById(branchId)
				.orElseThrow(() -> new RuntimeException("Branch not found"));
		if (!branch.getOrganization().getId().equals(orgId)) {
			throw new RuntimeException("Branch does not belong to given organization");
		}
		branch.setBranchCode(dto.getBranchCode());
		branch.setBranchName(dto.getBranchName());
		branch.setBranchContact(dto.getBranchContact());
		branch.setBranchAddress(OrganizationMapper.toEntity(dto.getBranchAddress()));
		Branch updated = branchRepository.save(branch);
		return OrganizationMapper.toBranchDto(updated);
	}
	public void deleteOrganization(Long orgId) {
		if (!organizationRepository.existsById(orgId)) {
			throw new RuntimeException("Organization not found");
		}
		organizationRepository.deleteById(orgId);
	}
	public void deleteBranch(Long orgId, Long branchId) {
		Branch branch = branchRepository.findById(branchId)
				.orElseThrow(() -> new RuntimeException("Branch not found"));
		if (!branch.getOrganization().getId().equals(orgId)) {
			throw new RuntimeException("Branch does not belong to given organization");
		}
		branchRepository.deleteById(branchId);
	}
}
