package com.watermelon.entity;

import com.watermelon.enums.EPermission;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="permissions")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Permission {

	@Id
	@Enumerated(EnumType.STRING)
	private EPermission id;
}
