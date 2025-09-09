package com.jts.login.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

/**
 *@author Rahim Sheik
 *@created 07-Sept-2025
 *
 * Request DTO for creating/updating an Employee.
 */
@Getter
@Setter
public class EmployeeRequest {

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

    // Only required if isAppUser = true
    private String username;
    private String password;
}

