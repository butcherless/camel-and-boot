package dev.cmartin.learn.camelapp.api;

import java.time.Instant;
import java.util.UUID;

public interface Model {
    record Position(
            Double longitude,
            Double latitude,
            UUID deviceId,
            Instant instant
    ) {
    }

    class InvalidPositionException extends RuntimeException {
        public InvalidPositionException(final String message) {
            super(message);
        }
    }


}
