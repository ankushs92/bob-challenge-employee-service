package com.takeaway.challenge.exception;

public enum TakeawayError {
    GEN_01("Resource does not exist"),
    D_01("Department with name already exists"),

    E_01("Employee with email already exists");

    private final String desc;

    TakeawayError(final String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
