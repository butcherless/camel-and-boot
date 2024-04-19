package dev.cmartin.learn.camelapp.repository;

import dev.cmartin.learn.camelapp.repository.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository
        extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByName(String name);
}
