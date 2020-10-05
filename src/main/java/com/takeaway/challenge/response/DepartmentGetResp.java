package com.takeaway.challenge.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.takeaway.challenge.domain.Department;
import com.takeaway.challenge.util.Assert;

public class DepartmentGetResp {

    private final int id;
    private final String name;

    public DepartmentGetResp(
            @JsonProperty("id") final int id,
            @JsonProperty("name")final String name
    )
    {
        this.id = id;
        this.name = name;
    }

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
