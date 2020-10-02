package com.takeaway.challenge.exception;

public enum TakeawayError {
    D_01("Department with name already exists");

    private final String desc;

    TakeawayError(final String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
