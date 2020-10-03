package com.takeaway.challenge.exception;

import org.springframework.http.HttpStatus;

public class TakeawayException extends RuntimeException {

    private final TakeawayError errorCode;
    private final Exception ex;
    private final HttpStatus status;

    public TakeawayException(final String msg, final HttpStatus status) {
        super(msg);
        this.status = status;
        this.errorCode = null;
        this.ex = null;
    }


    public TakeawayException(final TakeawayError errorCode, final HttpStatus status) {
        this(errorCode, null, status);
    }


    public TakeawayException(
            final TakeawayError errorCode,
            final Exception ex,
            final HttpStatus status
    )
    {
        super(errorCode.getDesc());
        this.errorCode = errorCode;
        this.ex = ex;
        this.status = status;
    }

    public TakeawayError getErrorCode() {
        return errorCode;
    }

    public Exception getEx() {
        return ex;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
