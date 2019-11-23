package com.company.exception;

public class WrongDataException extends DataManipulationException {
    private static final String WRONG_DATA_EXCEPTION_MESSAGE = "Wrong data in line ";

    public WrongDataException() {
        super();
    }

    public WrongDataException(Integer lineNumber) {
        super(WRONG_DATA_EXCEPTION_MESSAGE + lineNumber);
    }
}
