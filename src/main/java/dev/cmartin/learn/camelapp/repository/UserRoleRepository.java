package dev.cmartin.learn.camelapp.repository;

import dev.cmartin.learn.camelapp.repository.model.UserRoleEntity;
import dev.cmartin.learn.camelapp.repository.model.UserRoleKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository
        extends JpaRepository<UserRoleEntity, UserRoleKey> {

}
