package me.angelstoyanov.sporton.management.pitch.exception;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class PitchNotExistsException extends RuntimeException {
    public PitchNotExistsException(String message) {
        super(message);
    }

    public PitchNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
