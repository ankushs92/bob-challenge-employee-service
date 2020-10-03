package com.takeaway.challenge.response;

import java.util.UUID;

public class EmployeeAddResp {

    private final UUID employeeId;

    public EmployeeAddResp(final UUID employeeId) {
        this.employeeId = employeeId;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }
}
