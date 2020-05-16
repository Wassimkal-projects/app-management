package com.axeane.appmanagement.web.rest;

import com.axeane.appmanagement.domain.Module;
import com.axeane.appmanagement.service.interfaces.IModuleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/module")
public class ModuleResource {

    private final IModuleService moduleService;

    public ModuleResource(IModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @PostMapping("/saveModule")
    public ResponseEntity saveModule(@Valid @RequestBody Module module) {
        return new ResponseEntity<>(moduleService.saveModule(module), HttpStatus.CREATED);
    }

    @PutMapping("/updateModule")
    public ResponseEntity updateModule(@Valid @RequestBody Module module) {
        return new ResponseEntity<>(moduleService.updateModule(module), HttpStatus.CREATED);

    }

    @GetMapping("/getModuleById/{id}")
    public ResponseEntity getModuleById(@PathVariable Long id) {
        return new ResponseEntity<>(moduleService.getModuleById(id), HttpStatus.OK);
    }

    @GetMapping("/getAllModulesByApp/{id}")
    public ResponseEntity getAllModuleByApp(@PathVariable Long id) {
        return new ResponseEntity<>(moduleService.getAllModule(id), HttpStatus.OK);
    }

    @DeleteMapping("deleteModuleById/{id}")
    public ResponseEntity deleteModuleById(@PathVariable Long id) {
        moduleService.deleteModuleById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
