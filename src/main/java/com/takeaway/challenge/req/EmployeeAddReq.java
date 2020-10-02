package com.takeaway.challenge.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.takeaway.challenge.jackson.LocalDateDeserializer;

import java.time.LocalDate;

public class EmployeeAddReq {

    private final String email;
    private final String name;
    private final LocalDate birthday;
    private final int departmentId;


    public EmployeeAddReq(
            @JsonProperty("email") final String email,
            @JsonProperty("name") final String name,

            @JsonProperty("birthday")
            @JsonDeserialize(using = LocalDateDeserializer.class)
            final LocalDate birthday,

            @JsonProperty("departmentId") final int departmentId
    )
    {
        this.email = email;
        this.name = name;
        this.birthday = birthday;
        this.departmentId = departmentId;
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

    public int getDepartmentId() {
        return departmentId;
    }
}
