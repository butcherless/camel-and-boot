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
        name = "roles",
        indexes = {
                @Index(name = "role_name_index", columnList = "name", unique = true)
        }
)
@Entity
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_gen")
    private Long id = 0L;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;

}
