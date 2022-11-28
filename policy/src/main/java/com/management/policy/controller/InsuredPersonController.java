package com.management.policy.controller;

import com.management.policy.model.InsuredPerson;
import com.management.policy.model.dto.InsuredPersonDto;
import com.management.policy.service.InsuredPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/insuredPersons")
public class InsuredPersonController {
    private final InsuredPersonService insuredPersonService;

    @Autowired
    public InsuredPersonController(InsuredPersonService insuredPersonService) {
        this.insuredPersonService = insuredPersonService;
    }

    @PostMapping
    public ResponseEntity<InsuredPersonDto> addInsuredPerson(@RequestBody final InsuredPersonDto insuredPersonDto) {
        InsuredPerson insuredPerson = insuredPersonService.addInsuredPerson(InsuredPerson.from(insuredPersonDto));
        return new ResponseEntity<>(InsuredPersonDto.from(insuredPerson), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<InsuredPersonDto>> getInsuredPersons() {
        List<InsuredPerson> insuredPersons =  insuredPersonService.getInsuredPersons();
        List<InsuredPersonDto> insuredPersonsDto = insuredPersons.stream().map(InsuredPersonDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(insuredPersonsDto, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<InsuredPersonDto> getInsuredPerson(@PathVariable final int id) {
        InsuredPerson insuredPerson = insuredPersonService.getInsuredPerson(id);
        return new ResponseEntity<>(InsuredPersonDto.from(insuredPerson), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<InsuredPersonDto> deleteInsuredPerson(@PathVariable final int id) {
        InsuredPerson insuredPerson = insuredPersonService.deleteInsuredPerson(id);
        return new ResponseEntity<>(InsuredPersonDto.from(insuredPerson), HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<InsuredPersonDto> editInsuredPerson(@PathVariable final int id,
                                                              @RequestBody InsuredPersonDto insuredPersonDto) {
        InsuredPerson editInsuredPerson = insuredPersonService.editInsuredPerson(id, InsuredPerson.from(insuredPersonDto));
        return new ResponseEntity<>(InsuredPersonDto.from(editInsuredPerson), HttpStatus.OK);
    }


}
