package me.angelstoyanov.sporton.management.pitch.exception;

public class PitchAlreadyExistsException extends RuntimeException {
    public PitchAlreadyExistsException(String message) {
        super(message);
    }

    public PitchAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
