package dev.cmartin.learn.camelapp.repository;

import dev.cmartin.learn.camelapp.repository.model.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResourceRepository
        extends JpaRepository<ResourceEntity, Long> {

    Optional<ResourceEntity> findByName(String name);
}
