package com.watermelon.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.watermelon.listener.CustomAuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(CustomAuditingEntityListener.class)
public class AbstractAuditEntity {
	@JsonIgnore
	@CreationTimestamp
	@Column(name="created_on")
	private LocalDate createdOn;
	@JsonIgnore
	@CreatedBy
	@Column(name="created_by")
	private String createdBy;
	@JsonIgnore
	@UpdateTimestamp
	@Column(name="last_mofified_on")
	private LocalDate lastMofifiedOn;
	@JsonIgnore
	@LastModifiedBy
	@Column(name="last_modified_by")
	private String lastModifiedBy;
	

}
