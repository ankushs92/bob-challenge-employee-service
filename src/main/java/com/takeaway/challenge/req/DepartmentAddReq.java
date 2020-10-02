package com.takeaway.challenge.req;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartmentAddReq {

    private final String name;

    public DepartmentAddReq(@JsonProperty("name") final String name) {
        this.name = name;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "DepartmentAddReq{" +
                "name='" + name + '\'' +
                '}';
    }
}
