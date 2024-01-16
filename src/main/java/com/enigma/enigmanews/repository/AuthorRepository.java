package com.enigma.enigmanews.repository;

import com.enigma.enigmanews.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, String> {

    Author findIdAuthorByName(String name);
}
