package com.jts.login.common;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

/**
 *@author Rahim Sheik
 *@created 07-Sept-2025
 */

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @Column(name = "created_by_id", updatable = false)
    protected Long createdById;

    @Column(name = "updated_by_id")
    protected Long updatedById;

    @Column(name = "branch_id")
    protected Long branchId;

    @Column(name = "created_date", updatable = false)
    protected Instant createdDate;

    @Column(name = "updated_date")
    protected Instant updatedDate;

    @Column(name = "active", nullable = false)
    protected Boolean active = Boolean.TRUE;

    @Version
    @Column(name = "version")
    protected Long version;

    @PrePersist
    protected void onCreate() {
    	Instant now = Instant.now();
    	this.createdDate = now;
    	this.updatedDate = null;
    	if (this.active == null) 
    		this.active = Boolean.FALSE;

    	if (this.createdById == null) 
    		this.createdById = -1L;
    	this.updatedById = null;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = Instant.now();
        if (this.updatedById == null) this.updatedById = -1L;
    }
}
