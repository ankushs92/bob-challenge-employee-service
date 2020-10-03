package com.takeaway.challenge.req;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Optional;

public class EmployeeUpdateReq {

    private final String email;
    private final String name;
    private final LocalDate birthday;
    private final Integer departmentId;

    public EmployeeUpdateReq(
            @JsonProperty("email") final String email,
            @JsonProperty("name") final String name,

            @JsonProperty("birthday")
//            @JsonDeserialize(using = LocalDateDeserializer.class)
            final LocalDate birthday,
            @JsonProperty("departmentId")
            final Integer departmentId
    )
    {
        this.email = email;
        this.name = name;
        this.birthday = birthday;
        this.departmentId = departmentId;
    }

    public Optional<String> getEmail() {
        return Optional.ofNullable(email);
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<LocalDate> getBirthday() {
        return Optional.ofNullable(birthday);
    }

    public Optional<Integer> getDepartmentId() {
        return Optional.ofNullable(departmentId);
    }


}
