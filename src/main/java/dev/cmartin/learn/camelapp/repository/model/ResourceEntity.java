package dev.cmartin.learn.camelapp.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents frontend components, backend operations, or any other resources that need protection.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "resources",
        indexes = {
                @Index(name = "resource_name_index", columnList = "name", unique = true)
        }
)
@Entity
public class ResourceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resource_gen")
    private Long id = 0L;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;

}
