package com.takeaway.challenge.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.takeaway.challenge.domain.Employee;
import com.takeaway.challenge.util.Assert;

import java.time.LocalDate;
import java.util.UUID;

public class EmployeeResp {

    private final UUID id;
    private final String email;
    private final String name;
    private final LocalDate birthday;
    private final DepartmentGetResp department;

    //This constructor will be used by Jackson for deser in test cases
    public EmployeeResp(
            @JsonProperty("employeeId") final UUID id,
            @JsonProperty("email") final String email,
            @JsonProperty("name") final String name,
            @JsonProperty("birthday")final LocalDate birthday,
            @JsonProperty("department") final DepartmentGetResp department
    )
    {
        this.id = id;
        this.email = email;
        this.name = name;
        this.birthday = birthday;
        this.department = department;
    }

    public EmployeeResp(final Employee employee) {
        Assert.notNull(employee, "employee cannot be null");
        this.id = employee.getId();
        this.email = employee.getEmail();
        this.name = employee.getName();
        this.birthday = employee.getBirthday();
        this.department = new DepartmentGetResp(employee.getDepartment());
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public DepartmentGetResp getDepartment() {
        return department;
    }



}
