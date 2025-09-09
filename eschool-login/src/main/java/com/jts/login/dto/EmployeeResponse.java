package com.jts.login.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

/**
 *@author Rahim Sheik
 *@created 07-Sept-2025
 *
 * Response DTO for sending employee details to UI.
 */
@Getter
@Setter
public class EmployeeResponse {

    private Long id;
    private String employeeCode;
    private String employeeName;
    private String qualification;
    private String category;
    private LocalDate dob;
    private Integer age;
    private String primaryContact;
    private String secondaryContact;
    private String address;
    private String gender;
    private String photograph;
    private Boolean isAppUser;

    // If linked user exists
    private Integer userId;
    private String username;
}

