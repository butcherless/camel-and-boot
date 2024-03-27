package dev.cmartin.learn.camelapp.api;

import java.time.Instant;

/**
 * Model de dominio para los errores de una capa de aplicación
 */
sealed interface ErrorModel {
    /**
     * property común
     */
    String message = "";
}

/**
 * @param message detalle del error
 */
record BadRequest(String message) implements ErrorModel {
}

/**
 * @param message detalle del error
 * @param instant instante del acceso no permitido, property específica
 */
record Unauthorized(String message, Instant instant) implements ErrorModel {
}

record Forbidden(String message, Instant instant) implements ErrorModel {
}

record NotFound(String message) implements ErrorModel {
}

record Conflict(String message) implements ErrorModel {
}
