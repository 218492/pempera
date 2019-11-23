package com.company.exception;

public abstract class DataManipulationException extends Exception {
    DataManipulationException() {
        super();
    }

    DataManipulationException(String message) {
        super(message);
    }
}
