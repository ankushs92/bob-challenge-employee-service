package com.takeaway.challenge.service.impl;

import com.takeaway.challenge.constants.KafkaTopic;
import com.takeaway.challenge.domain.EmployeeEvent;
import com.takeaway.challenge.service.EmployeeEventService;
import com.takeaway.challenge.util.Assert;
import com.takeaway.challenge.util.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeEventServiceKafkaImpl implements EmployeeEventService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeEventServiceKafkaImpl.class);
    private final KafkaTemplate<String, String> kafka;

    EmployeeEventServiceKafkaImpl(final KafkaTemplate<String, String> kafka) {
        this.kafka = kafka;
    }

    @Override
    @Async
    public ListenableFuture<Map<String, String>> sendMessage(final EmployeeEvent employeeEvent) {
        Assert.notNull(employeeEvent, "employeeEvent cannot be null");
        //Since we are working with only one partition, no key needs to be specified
        var json = Json.encode(employeeEvent);
        var metaData = new HashMap<String, String>();
        kafka.send(KafkaTopic.EMPLOYEE_EVENT, json)
             .addCallback(
                     (success) -> {
                         var recordMetaData = success.getRecordMetadata();
                         metaData.put("offset", String.valueOf(recordMetaData.offset()));
                         metaData.put("topic", recordMetaData.topic());
                     },
                     (failure) -> logger.error("Could not push message to Kafka :", failure)
             );

        return new AsyncResult<>(metaData);
    }
}
