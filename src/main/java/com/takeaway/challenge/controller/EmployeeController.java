package com.takeaway.challenge.controller;


import com.takeaway.challenge.exception.TakeawayError;
import com.takeaway.challenge.exception.TakeawayException;
import com.takeaway.challenge.req.EmployeeAddReq;
import com.takeaway.challenge.req.EmployeeUpdateReq;
import com.takeaway.challenge.response.EmployeeAddResp;
import com.takeaway.challenge.response.EmployeeGetOrUpdateResp;
import com.takeaway.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    EmployeeController(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody final EmployeeAddReq addReq) {
        logger.debug("EmployeeAddReq {}", addReq);
        var employee = employeeService.create(addReq);
        var resp = new EmployeeAddResp(employee.getId());
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{uuid}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable final String uuid) {
        logger.debug("Find By UUID {}", uuid);
        var employee = employeeService.find(UUID.fromString(uuid));
        if (employee.isEmpty()) {
            throw new TakeawayException(TakeawayError.GEN_01, HttpStatus.NOT_FOUND);
        }
        var resp = new EmployeeGetOrUpdateResp(employee.get());
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{uuid}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteById(@PathVariable final String uuid) {
        logger.debug("Delete By {}", uuid);
        employeeService.delete(UUID.fromString(uuid));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{uuid}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateById(
            @PathVariable final String uuid,
            @RequestBody  final EmployeeUpdateReq updateReq)
    {
        logger.debug("Update By {}. Req : {}", uuid, updateReq);
        var employee = employeeService.update(UUID.fromString(uuid), updateReq);
        var resp = new EmployeeGetOrUpdateResp(employee);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
