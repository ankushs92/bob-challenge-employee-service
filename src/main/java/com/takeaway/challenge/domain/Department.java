package com.takeaway.challenge.domain;

import com.takeaway.challenge.req.DepartmentAddReq;
import com.takeaway.challenge.util.Assert;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String name;

    @Column(name = "created_on")
    private ZonedDateTime created;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;

    public Department() {}

    public Department(final DepartmentAddReq addReq) {
        Assert.notNull(addReq, "addReq cannot be null");
        this.name = addReq.getName();
        this.created = ZonedDateTime.now(ZoneId.of("UTC"));
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}
