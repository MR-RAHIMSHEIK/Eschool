package com.jts.login.mapper;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.jts.login.domain.Address;
import com.jts.login.domain.Branch;
import com.jts.login.domain.Organization;
import com.jts.login.dto.AddressDTO;
import com.jts.login.dto.BranchRequestDTO;
import com.jts.login.dto.BranchResponseDTO;
import com.jts.login.dto.OrganizationRequestDTO;
import com.jts.login.dto.OrganizationResponseDTO;

/**
 *@author Rahim Sheik
 *@created 01-Sept-2025
 */
public class OrganizationMapper {

    public static Address toEntity(AddressDTO dto) {
        if (dto == null) return null;
        return Address.builder()
                .dno(dto.getDno())
                .addressLine(dto.getAddressLine())
                .villageOrCity(dto.getVillageOrCity())
                .mandal(dto.getMandal())
                .district(dto.getDistrict())
                .state(dto.getState())
                .country(dto.getCountry())
                .build();
    }

    public static AddressDTO toDto(Address entity) {
        if (entity == null) return null;
        return AddressDTO.builder()
                .dno(entity.getDno())
                .addressLine(entity.getAddressLine())
                .villageOrCity(entity.getVillageOrCity())
                .mandal(entity.getMandal())
                .district(entity.getDistrict())
                .state(entity.getState())
                .country(entity.getCountry())
                .build();
    }

    public static Organization toEntity(OrganizationRequestDTO dto) {
        return Organization.builder()
                .organizationCode(dto.getOrganizationCode())
                .organizationName(dto.getOrganizationName())
                .organizationContact(dto.getOrganizationContact())
                .organizationAddress(toEntity(dto.getOrganizationAddress()))
                .build();
    }

    public static OrganizationResponseDTO toDto(Organization entity) {
    	return OrganizationResponseDTO.builder()
    			.id(entity.getId())
    			.organizationCode(entity.getOrganizationCode())
    			.organizationName(entity.getOrganizationName())
    			.organizationContact(entity.getOrganizationContact())
    			.organizationAddress(toDto(entity.getOrganizationAddress()))
    			.branches( entity.getBranches() == null
    			? new ArrayList<>()
    					: entity.getBranches().stream()
    					.map(OrganizationMapper::toBranchDto)
    					.collect(Collectors.toList()))
    			.build();
    }

    public static Branch toEntity(BranchRequestDTO dto, Organization org) {
        return Branch.builder()
                .branchCode(dto.getBranchCode())
                .branchName(dto.getBranchName())
                .branchContact(dto.getBranchContact())
                .branchAddress(toEntity(dto.getBranchAddress()))
                .organization(org)
                .build();
    }

    public static BranchResponseDTO toBranchDto(Branch entity) {
        return BranchResponseDTO.builder()
                .id(entity.getId())
                .branchCode(entity.getBranchCode())
                .branchName(entity.getBranchName())
                .branchContact(entity.getBranchContact())
                .branchAddress(toDto(entity.getBranchAddress()))
                .build();
    }
}
