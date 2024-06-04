package com.watermelon.entity;

import java.util.HashSet;
import java.util.Set;

import com.watermelon.enums.ERole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="roles")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

	@Id
	@Enumerated(EnumType.STRING)
	private ERole id;
	
	@Builder.Default
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "role_pers", 
	joinColumns = @JoinColumn(name = "role_id"),
	inverseJoinColumns = @JoinColumn(name = "per_id"))
	private Set<Permission> listPermissions = new HashSet<>();
}
