package com.jts.login.domain;


import java.time.LocalDate;

import com.jts.login.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Index;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *@author Rahim Sheik
 *@created 07-Sept-2025
 * Employee entity for HR module.
 */
@Entity
@Table(
    name = "employees",
    indexes = {
        @Index(name = "idx_employee_code", columnList = "employee_code"),
        @Index(name = "idx_primary_contact", columnList = "primary_contact")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class Employee extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_code", nullable = false, unique = true, length = 100)
    private String employeeCode;

    @Column(name = "employee_name", nullable = false)
    private String employeeName;

    @Column(name = "qualification")
    private String qualification;

    @Column(name = "category")
    private String category;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "age")
    private Integer age;

    @Column(name = "primary_contact", length = 50)
    private String primaryContact;

    @Column(name = "secondary_contact", length = 50)
    private String secondaryContact;

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "is_app_user", nullable = false)
    private Boolean isAppUser = Boolean.FALSE;
    
    @OneToOne(mappedBy = "employee", fetch = FetchType.LAZY)
    private User user;
    
    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "photograph", length = 500)
    private String photograph; // e.g., "/uploads/employees/emp001.jpg"



}

