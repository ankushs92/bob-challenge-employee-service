package com.takeaway.challenge.controller;


import com.takeaway.challenge.req.DepartmentAddReq;
import com.takeaway.challenge.response.DepartmentAddResp;
import com.takeaway.challenge.service.DepartmentService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("department")
@Api(tags = "Department")
public class DepartmentController {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    private final DepartmentService departmentService;

    DepartmentController(final DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @ApiOperation(value = "Add Department")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Department successfully created"),
            @ApiResponse(code = 403, message = "A department with the name you passed already exists!"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DepartmentAddResp> create(
            @RequestBody
            @ApiParam(value = "Pass the required parameters as a JSON to create a department")
            final DepartmentAddReq req
    )
    {
        logger.debug("DepartmentAddReq {}", req);
        var department = departmentService.create(req);
        var resp = new DepartmentAddResp(department);
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

}
