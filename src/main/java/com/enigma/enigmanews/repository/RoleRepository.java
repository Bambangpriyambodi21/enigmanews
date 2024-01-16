package com.enigma.enigmanews.repository;

import com.enigma.enigmanews.constant.ERole;
import com.enigma.enigmanews.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRole(ERole role);
}
