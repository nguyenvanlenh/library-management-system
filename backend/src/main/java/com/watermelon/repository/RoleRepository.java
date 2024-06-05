package com.watermelon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.watermelon.entity.Role;
import com.watermelon.enums.ERole;

public interface RoleRepository extends JpaRepository<Role, ERole> {

}
