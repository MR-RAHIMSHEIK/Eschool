package com.jts.login.domain;

import java.util.Set;

import com.jts.login.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *@author Rahim Sheik
 *@created 07-Sept-2025
 */
@Entity
@Table(name = "country")
@Getter
@Setter
public class Country extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", length=200, nullable=false)
    private String name;

    @Column(name="code", length=50)
    private String code;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    private Set<State> states;
}
