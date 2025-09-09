package com.jts.login.domain;

import java.util.Set;

import com.jts.login.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *@author Rahim Sheik
 *@created 07-Sept-2025
 */
@Entity
@Table(name = "mandal")
@Getter
@Setter
public class Mandal extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="district_id", nullable=false, updatable=false)
    private District district;

    @Column(nullable=false, length=200)
    private String name;

    @Column(length=50)
    private String code;

    @OneToMany(mappedBy = "mandal", fetch = FetchType.LAZY)
    private Set<VillageCity> villagesCities;
}
