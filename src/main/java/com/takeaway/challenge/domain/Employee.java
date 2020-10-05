package com.takeaway.challenge.domain;

import com.takeaway.challenge.req.EmployeeAddReq;
import com.takeaway.challenge.util.Assert;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(generator = "UUID")
    @Type(type="uuid-char") // For human readable uuid in mysql, otherwise it's binary
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "full_name", nullable = false)
    private String name;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @ManyToOne
    @JoinColumn(name = "dept_id")
    private Department department;

    @Column(name = "created_on", nullable = false)
    private ZonedDateTime created;

    @Column(name = "updated", nullable = false)
    private ZonedDateTime updated;

    public Employee() {}

    public Employee(final EmployeeAddReq add, final Department department) {
        Assert.notNull(add, "EmployeeAddReq cannot be null");
        this.email = add.getEmail();
        this.name = add.getName();
        this.birthday = add.getBirthday();

        var now = ZonedDateTime.now(ZoneId.of("UTC"));
        this.created = now;
        this.updated = now;
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

    public ZonedDateTime getUpdated() {
        return updated;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
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
                ", updated=" + updated +
                '}';
    }
}
