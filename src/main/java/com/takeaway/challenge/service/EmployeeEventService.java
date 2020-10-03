package com.takeaway.challenge.service;

import com.takeaway.challenge.domain.EmployeeEvent;

public interface EmployeeEventService {

    void sendMessage(EmployeeEvent employeeEvent);

}
