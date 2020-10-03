package com.takeaway.challenge.domain;

import com.takeaway.challenge.req.EmployeeAddReq;
import com.takeaway.challenge.util.Assert;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(generator = "uuid4")
    private UUID id;

    @Column(name = "email")
    private String email;

    @Column(name = "full_name")
    private String name;

    @Column(name = "birthday")
    private LocalDate birthday;

    @ManyToOne
    @JoinColumn(name = "dept_id")
    private Department department;

    @Column(name = "created_on")
    private ZonedDateTime created;

    public Employee() {}

    public Employee(final EmployeeAddReq add, final Department department) {
        Assert.notNull(add, "EmployeeAddReq cannot be null");
        this.email = add.getEmail();
        this.name = add.getName();
        this.birthday = add.getBirthday();
        this.created = ZonedDateTime.now(ZoneId.of("UTC"));
        this.department = department;
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

    public Department getDepartment() {
        return department;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", department=" + department +
                ", created=" + created +
                '}';
    }
}
