package com.takeaway.challenge.exception;

public class TakeawayException extends RuntimeException {

    private final TakeawayError errorCode;
    private final Exception ex;

    public TakeawayException(final String msg) {
        super(msg);
        this.errorCode = null;
        this.ex = null;
    }


    public TakeawayException(final TakeawayError errorCode) {
        this(errorCode, null);
    }


    public TakeawayException(
            final TakeawayError errorCode,
            final Exception ex
    )
    {
        super(errorCode.getDesc());
        this.errorCode = errorCode;
        this.ex = ex;
    }

//    public TakeawayError getErrorCode() {
//        return errorCode;
//    }
//
//    public Exception getEx() {
//        return ex;
//    }
}
