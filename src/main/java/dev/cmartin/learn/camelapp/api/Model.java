package dev.cmartin.learn.camelapp.api;

import java.time.Instant;
import java.util.UUID;

public interface Model {
    enum DeviceType {
        PLANE, ENGINE
    }

    record Device(
            UUID id,
            DeviceType type,
            String description
    ) {
    }

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

    class InvalidDeviceException extends RuntimeException {
        public InvalidDeviceException(final String message) {
            super(message);
        }
    }

}
