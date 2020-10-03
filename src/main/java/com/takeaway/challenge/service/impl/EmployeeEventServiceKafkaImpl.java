package com.takeaway.challenge.service.impl;

import com.takeaway.challenge.constants.KafkaTopic;
import com.takeaway.challenge.domain.EmployeeEvent;
import com.takeaway.challenge.service.EmployeeEventService;
import com.takeaway.challenge.util.Assert;
import com.takeaway.challenge.util.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmployeeEventServiceKafkaImpl implements EmployeeEventService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeEventServiceKafkaImpl.class);
    private final KafkaTemplate<String, String> kafka;

    EmployeeEventServiceKafkaImpl(final KafkaTemplate<String, String> kafka) {
        this.kafka = kafka;
    }

    @Override
    public void sendMessage(final EmployeeEvent employeeEvent) {
        Assert.notNull(employeeEvent, "employeeEvent cannot be null");
        //Since we are working with only one partition, no key needs to be specified
        //Also, we serialize the object to JSON and then send it to the topic
        var json = Json.encode(employeeEvent);
        kafka.send(KafkaTopic.EMPLOYEE_EVENT, json)
             .addCallback(
                     (success) -> {}, // do something if you wish if operation is a success
                     (failure) -> logger.error("Could not push message to Kafka :", failure)
             );
    }
}
