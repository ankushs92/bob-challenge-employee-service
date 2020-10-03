package com.takeaway.challenge.response;

import com.takeaway.challenge.domain.Department;
import com.takeaway.challenge.domain.Employee;
import com.takeaway.challenge.util.Assert;

import java.time.LocalDate;
import java.util.UUID;

public class EmployeeGetOrUpdateResp {

    private final UUID id;
    private final String email;
    private final String name;
    private final LocalDate birthday;
    private final DepartmentGetResp department;

    public EmployeeGetOrUpdateResp(final Employee employee) {
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

    private static class DepartmentGetResp {

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


}
