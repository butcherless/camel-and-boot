package dev.cmartin.learn.camelapp.repository;

import dev.cmartin.learn.camelapp.repository.model.OperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OperationRepository
        extends JpaRepository<OperationEntity, Long> {

    Optional<OperationEntity> findByName(String name);

}
