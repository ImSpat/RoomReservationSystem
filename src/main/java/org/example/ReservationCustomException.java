package org.example;

public abstract class ReservationCustomException extends RuntimeException {

    public ReservationCustomException(String message) {
        super(message);
    }

    abstract int getCode();
}
