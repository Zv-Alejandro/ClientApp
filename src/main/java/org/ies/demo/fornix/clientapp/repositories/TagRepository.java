package org.ies.demo.fornix.clientapp.repositories;

import org.ies.demo.fornix.clientapp.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
}