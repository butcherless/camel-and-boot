package dev.cmartin.learn.camelapp.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "operations",
        indexes = {
                @Index(name = "operation_name_index", columnList = "name", unique = true)
        }
)
@Entity
public class OperationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operation_gen")
    private Long id = 0L;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;

}
