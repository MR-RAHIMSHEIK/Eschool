package com.jts.login.domain;

import com.jts.login.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *@author Rahim Sheik
 *@created 07-Sept-2025
 */
@Entity
@Table(name = "village_city")
@Getter
@Setter
public class VillageCity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="mandal_id", nullable=false, updatable=false)
    private Mandal mandal;

    @Column(nullable=false, length=200)
    private String name;

    @Column(length=50)
    private String code;

    @Column(name="pin_code", length=6, nullable=false)
    private String pinCode;
}
