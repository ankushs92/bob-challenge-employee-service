package com.takeaway.challenge.response;

import com.takeaway.challenge.domain.Department;
import com.takeaway.challenge.util.Assert;

public class DepartmentAddResp {

    private final int departmentId;
    private final String name;

    public DepartmentAddResp(final Department department) {
        Assert.notNull(department, "Department cannot be null");
        this.departmentId = department.getId();
        this.name = department.getName();
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public String getName() {
        return name;
    }
}
