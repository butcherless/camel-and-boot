package dev.cmartin.learn.camelapp.repository;

import dev.cmartin.learn.camelapp.repository.dto.ResourceOperation;
import dev.cmartin.learn.camelapp.repository.model.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PermissionRepository
        extends JpaRepository<PermissionEntity, Long> {

    List<PermissionEntity> findByUserName(String username);

    @Query("""
            select new dev.cmartin.learn.camelapp.repository.dto.ResourceOperation(
            re.name,
            op.name)
            from PermissionEntity pe
            left join ResourceEntity re on pe.resource.id = re.id
            left join OperationEntity op on pe.operation.id = op.id
            where pe.user.name = :username
            """)
    List<ResourceOperation> findROByUserName(@Param("username") String username);
}
