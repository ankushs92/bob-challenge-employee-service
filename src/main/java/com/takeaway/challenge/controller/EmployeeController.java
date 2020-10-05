package com.takeaway.challenge.controller;


import com.takeaway.challenge.exception.TakeawayError;
import com.takeaway.challenge.exception.TakeawayException;
import com.takeaway.challenge.req.EmployeeAddReq;
import com.takeaway.challenge.req.EmployeeUpdateReq;
import com.takeaway.challenge.response.EmployeeResp;
import com.takeaway.challenge.service.EmployeeService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("employee")
@Api(tags = "Employee")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    EmployeeController(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @ApiOperation(value = "Add Employee")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Employee successfully created"),
            @ApiResponse(code = 403, message = "Either Malformed Jwt Token or the Employee with the given email already exists"),
            @ApiResponse(code = 404, message = "The departmentId you passed does not exist"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResp> create(@RequestBody final EmployeeAddReq addReq) {
        logger.debug("EmployeeAddReq {}", addReq);
        var employee = employeeService.create(addReq);
        var resp = new EmployeeResp(employee);
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }


    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @ApiOperation(value = "Get Employee By UUID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee info successfully returned"),
            @ApiResponse(code = 403, message = "Malformed or no JWT Token passed"),
            @ApiResponse(code = 404, message = "The Employee with passed employeeId does not exist"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResp> findById(@PathVariable final String employeeId) {
        logger.debug("Find By UUID {}", employeeId);
        var employee = employeeService.find(UUID.fromString(employeeId))
                                      .orElseThrow(() -> new TakeawayException(TakeawayError.GEN_01, HttpStatus.NOT_FOUND));

        var resp = new EmployeeResp(employee);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }


    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @ApiOperation(value = "Delete Employee By UUID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Employee deleted successfully"),
            @ApiResponse(code = 403, message = "Malformed or no JWT Token passed"),
            @ApiResponse(code = 404, message = "The Employee with passed employeeId does not exist"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @DeleteMapping(value = "/{employeeId}")
    public ResponseEntity<Void> deleteById(@PathVariable final String employeeId) {
        logger.debug("Delete By {}", employeeId);
        employeeService.delete(UUID.fromString(employeeId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @ApiOperation(value = "Update Employee By UUID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Employee updated successfully"),
            @ApiResponse(code = 403, message = "Malformed or no JWT Token passed"),
            @ApiResponse(code = 404, message = "The Employee with passed employeeId does not exist"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PutMapping(value = "/{employeeId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResp> updateById(
            @PathVariable final String employeeId,
            @RequestBody  final EmployeeUpdateReq updateReq)
    {
        logger.debug("Update By {}. Req : {}", employeeId, updateReq);
        var employee = employeeService.update(UUID.fromString(employeeId), updateReq);
        var resp = new EmployeeResp(employee);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
