package com.jts.login.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *@author Rahim Sheik
 *@created 01-Sept-2025
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizationResponseDTO {
    private Long id;
    private String organizationCode;
    private String organizationName;
    private String organizationContact;
    private AddressDTO organizationAddress;
    private List<BranchResponseDTO> branches;
}
