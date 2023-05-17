package com.example.telrostest.repository;

import com.example.telrostest.model.role.ERole;
import com.example.telrostest.model.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(ERole roleName);
}
