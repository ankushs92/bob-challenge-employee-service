package com.takeaway.challenge.response;

import com.takeaway.challenge.domain.Department;
import com.takeaway.challenge.util.Assert;

public class DepartmentGetResp {

    private final int id;
    private final String name;

    DepartmentGetResp(final Department department) {
        Assert.notNull(department, "department cannot be null");
        this.id = department.getId();
        this.name = department.getName();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
