package com.takeaway.challenge.advice;

import java.time.LocalDate;

public class ErrorMessage {

    private final LocalDate timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;

    ErrorMessage(
            final LocalDate timestamp,
            final int status,
            final String error,
            final String message,
            final String path
    )
    {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
