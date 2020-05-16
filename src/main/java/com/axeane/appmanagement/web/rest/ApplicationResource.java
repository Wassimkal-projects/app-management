package com.axeane.appmanagement.web.rest;

import com.axeane.appmanagement.domain.Application;
import com.axeane.appmanagement.service.interfaces.IApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/application")
public class ApplicationResource {

    private final IApplicationService applicationService;

    public ApplicationResource(IApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/saveApplication")
    public ResponseEntity saveApplication(@Valid @RequestBody Application app) {
        return new ResponseEntity<>(applicationService.saveApplication(app), HttpStatus.CREATED);
    }

    @PutMapping("/updateApplication")
    public ResponseEntity updateApplication(@Valid @RequestBody Application app){
        return new ResponseEntity<>(applicationService.updateApplication(app), HttpStatus.OK);
    }

    @GetMapping("/getApplicationById/{id}")
    public ResponseEntity getApplicationById(@PathVariable Long id){
        return new ResponseEntity<>(applicationService.getApplicationById(id),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAllApplications(){
        return new ResponseEntity<>(applicationService.getAllApplications(),HttpStatus.OK);
    }

    @DeleteMapping("/deleteApplicationById/{id}")
    public ResponseEntity deleteApplicationById(@PathVariable Long id){
        applicationService.deleteApplicationById(id);
        return new ResponseEntity(HttpStatus.OK);
    }


}
