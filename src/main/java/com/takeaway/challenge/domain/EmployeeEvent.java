package com.takeaway.challenge.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.takeaway.challenge.domain.enums.CrudOp;
import com.takeaway.challenge.jackson.LocalDateSerializer;
import com.takeaway.challenge.jackson.ZonedDateTimeSerializer;
import com.takeaway.challenge.util.Assert;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

public class EmployeeEvent {

    private final UUID id;
    private final String email;
    private final String name;

    @JsonSerialize(using = LocalDateSerializer.class)
    private final LocalDate birthday;

    private final DepartmentEvent department;

    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    private final ZonedDateTime created;

    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    private final ZonedDateTime updated;

    private final CrudOp crudOp;

    public EmployeeEvent(final Employee employee, final CrudOp crudOp) {
        Assert.notNull(employee, "employee cannot be null");
        Assert.notNull(crudOp, "crudOp cannot be null");

        this.id = employee.getId();
        this.email = employee.getEmail();
        this.name = employee.getName();
        this.birthday = employee.getBirthday();
        this.department = new DepartmentEvent(employee.getDepartment());
        this.created = employee.getCreated();
        this.updated = employee.getUpdated();
        this.crudOp = crudOp;
    }

    private static class DepartmentEvent {
        private final int id;
        private final String name;

        public DepartmentEvent(final Department department) {
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

    public DepartmentEvent getDepartment() {
        return department;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }

    public CrudOp getCrudOp() {
        return crudOp;
    }
}
