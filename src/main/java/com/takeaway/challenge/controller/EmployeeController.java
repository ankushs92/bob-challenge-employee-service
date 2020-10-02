package com.takeaway.challenge.controller;


import com.takeaway.challenge.service.EmployeeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    EmployeeController(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


}
