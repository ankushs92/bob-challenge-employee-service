package com.takeaway.challenge.controller;


import com.takeaway.challenge.req.DepartmentAddReq;
import com.takeaway.challenge.response.DepartmentAddResp;
import com.takeaway.challenge.service.DepartmentService;
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
public class DepartmentController {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    private final DepartmentService departmentService;

    DepartmentController(final DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody final DepartmentAddReq req) {
        logger.debug("DepartmentAddReq {}", req);
        var department = departmentService.create(req);
        var resp = new DepartmentAddResp(department.getId());
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

}
