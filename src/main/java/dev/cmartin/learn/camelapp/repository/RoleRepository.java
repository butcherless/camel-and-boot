package dev.cmartin.learn.camelapp.repository;

import dev.cmartin.learn.camelapp.repository.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository
        extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(String name);
}
