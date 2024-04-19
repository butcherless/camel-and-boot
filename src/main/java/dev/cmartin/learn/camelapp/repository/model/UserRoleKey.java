package dev.cmartin.learn.camelapp.repository.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserRoleKey
        implements Serializable {
    private Long userId;
    private Long roleId;
}
