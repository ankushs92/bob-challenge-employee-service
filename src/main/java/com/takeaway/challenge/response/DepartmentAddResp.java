package com.takeaway.challenge.response;

public class DepartmentAddResp {

    private final int departmentId;

    public DepartmentAddResp(final int id) {
        this.departmentId = id;
    }

    public int getDepartmentId() {
        return departmentId;
    }

}
