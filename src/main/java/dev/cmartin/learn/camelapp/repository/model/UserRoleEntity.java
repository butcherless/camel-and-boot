package dev.cmartin.learn.camelapp.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents different roles that users can have, such as app-admin, company-admin, and user.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "users_roles"
)
@Entity
public class UserRoleEntity {
    @EmbeddedId
    private UserRoleKey id;

    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "roleId", insertable = false, updatable = false)
    private RoleEntity role;
}
