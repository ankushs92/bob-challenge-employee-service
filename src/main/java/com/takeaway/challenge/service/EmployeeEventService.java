package com.takeaway.challenge.service;

import com.takeaway.challenge.domain.EmployeeEvent;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Map;

public interface EmployeeEventService {

    //Most Spring boot libraries for message queues such as Rabbit MQ and Redis have their corresponding RabbitMQ template and Redis Template that
    //do not return anything when you call a sendMessage on them(the method is void). More over, I have kept this interface generic, as in any implementation of this class should return a ListenableFuture that
    //contains a Map<String, String> that might or might not contain metadata about that object.
    //Making this method return a generic  ListenableFuture<Map<String, String>> unburdens the caller of this method from changing its own code whenever an implementation changes
    ListenableFuture<Map<String, String>> sendMessage(EmployeeEvent employeeEvent);

}
