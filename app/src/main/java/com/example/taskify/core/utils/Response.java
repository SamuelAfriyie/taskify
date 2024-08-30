package com.example.taskify.core.utils;

public class Response<L, R> {
    // Private constructor to ensure subclassing
    private Response() {
    }

    // Subclass for the Success value
    public static final class Success<L, R> extends Response<L, R> {
        private final L value;

        public Success(L value) {
            this.value = value;
        }

        public L getValue() {
            return value;
        }
    }

    // Subclass for the Failure value
    public static final class Failure<L, R> extends Response<L, R> {
        private final R value;

        public Failure(R value) {
            this.value = value;
        }

        public R getValue() {
            return value;
        }
    }

    // Methods to check which type is currently holding the value
    public boolean isSuccess() {
        return this instanceof Success;
    }

    public boolean isFailure() {
        return this instanceof Failure;
    }

    // Factory methods for creating instances
    public static <L, R> Response<L, R> success(L value) {
        return new Success<>(value);
    }

    public static <L, R> Response<L, R> failure(R value) {
        return new Failure<>(value);
    }
}
