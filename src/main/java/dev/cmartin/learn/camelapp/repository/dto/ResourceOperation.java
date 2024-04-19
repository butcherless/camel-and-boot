package dev.cmartin.learn.camelapp.repository.dto;

public record ResourceOperation(
        String resourceName,
        String operationName
) {
}
