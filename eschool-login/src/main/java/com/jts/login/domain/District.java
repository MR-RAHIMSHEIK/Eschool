package com.jts.login.domain;

import com.jts.login.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 *@author Rahim Sheik
 *@created 07-Sept-2025
 */

@Entity
@Table(name = "district")
@Getter
@Setter
public class District extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="state_id", nullable=false, updatable=false)
    private State state;

    @Column(nullable=false, length=200)
    private String name;

    @Column(length=50)
    private String code;

    @OneToMany(mappedBy = "district", fetch = FetchType.LAZY)
    private Set<Mandal> mandals;
}

