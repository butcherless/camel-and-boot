package dev.cmartin.learn.camelapp.repository;

import dev.cmartin.learn.camelapp.repository.dto.ResourceOperation;
import dev.cmartin.learn.camelapp.repository.model.*;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UseCasesTestIT {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    String usernameOne = "user_one",
            usernameTwo = "user_two";
    List<String> users = List.of(usernameOne, usernameTwo);
    String challengeRes = "challenge",
            rankingRes = "ranking",
            userRes = "user";
    List<String> resources = List.of(challengeRes, rankingRes, userRes);

    String listOp = "list",
            createOp = "create",
            updateOp = "update",
            deleteOp = "delete",
            payOp = "pay";

    List<String> operations = List.of(listOp, createOp, updateOp, deleteOp, payOp);

    String userRole = "user",
            adminRole = "admin";
    List<String> roles = List.of(userRole, adminRole);

    @Test
    void should_check_model() {
        assertThat(userRepository).isNotNull();
        assertThat(resourceRepository).isNotNull();
        assertThat(operationRepository).isNotNull();
        assertThat(permissionRepository).isNotNull();

        assertThat(userRepository.count()).isEqualTo(users.size());
        assertThat(resourceRepository.count()).isEqualTo(resources.size());
        assertThat(operationRepository.count()).isEqualTo(operations.size());
    }

    @Test
    void should_create_permissions() {
        val user = getUser(usernameOne);
        createPermission(user, challengeRes, listOp);
        createPermission(user, challengeRes, createOp);

        // when
        List<PermissionEntity> permissions = permissionRepository.findByUserName(user.getName());

        List<ResourceOperation> resourceOperations = permissionRepository.findROByUserName(user.getName());

        // then
        assertThat(permissions)
                .size().isEqualTo(2);
        assertThat(resourceOperations)
                .size().isEqualTo(2);
    }

    @Test
    void should_create_user_role() {
        val user = getUser(usernameOne);
        val role = getRole(userRole);
        val key = new UserRoleKey(user.getId(), role.getId());
        val userRole = new UserRoleEntity(key, user, role);
        val entity = userRoleRepository.save(userRole);
        val x = 0;
    }

    private PermissionEntity createPermission(
            UserEntity user,
            String resource,
            String operation) {
        return permissionRepository.save(
                new PermissionEntity(0L, user,
                        getResource(resource),
                        getOperation(operation)));
    }

    private UserEntity getUser(String username) {
        return userRepository.findByName(username).orElseThrow();
    }

    private ResourceEntity getResource(String name) {
        return resourceRepository.findByName(name).orElseThrow();
    }

    private OperationEntity getOperation(String name) {
        return operationRepository.findByName(name).orElseThrow();
    }

    private RoleEntity getRole(String name) {
        return roleRepository.findByName(name).orElseThrow();
    }


    @BeforeEach
    void insertData() {
        users.forEach(name -> {
            val user = new UserEntity(0L, name);
            userRepository.save(user);
        });

        resources.forEach(name -> {
            val resource = new ResourceEntity(0L, name, name + " resource");
            resource.setName(name);
            resourceRepository.save(resource);
        });

        operations.forEach(name -> {
            val operation = new OperationEntity(0L, name, name + " operation");
            operationRepository.save(operation);
        });

        roles.forEach(name -> {
            val role = new RoleEntity(0L, name, name + " role");
            roleRepository.save(role);
        });
    }
}
