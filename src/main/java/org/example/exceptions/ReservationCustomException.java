package org.example.exceptions;

public abstract class ReservationCustomException extends RuntimeException {

    public ReservationCustomException(String message) {
        super(message);
    }

    public abstract int getCode();
}
