package com.enigma.enigmanews.repository;

import com.enigma.enigmanews.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TagRepository extends JpaRepository<Tag, String>, JpaSpecificationExecutor<Tag> {
    Tag findIdByName(String id);
}
