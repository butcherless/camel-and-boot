package dev.cmartin.learn.camelapp.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "permissions"
)
@Entity
public class PermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_gen")
    private Long id = 0L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ResourceEntity resource;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private OperationEntity operation;
}
