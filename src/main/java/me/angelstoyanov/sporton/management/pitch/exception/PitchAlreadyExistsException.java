package me.angelstoyanov.sporton.management.pitch.exception;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class PitchAlreadyExistsException extends RuntimeException {
    public PitchAlreadyExistsException(String message) {
        super(message);
    }

    public PitchAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
