package com.enigma.enigmanews.repository;

import com.enigma.enigmanews.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialRepository extends JpaRepository<UserCredential, String> {
    Optional<UserCredential> findByEmail(String email);
}
