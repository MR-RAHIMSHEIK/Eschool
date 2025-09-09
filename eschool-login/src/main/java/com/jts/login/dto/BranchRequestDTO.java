package com.jts.login.dto;

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
public class BranchRequestDTO {
    private String branchCode;
    private String branchName;
    private String branchContact;
    private AddressDTO branchAddress;
}