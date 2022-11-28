package com.management.policy.service;

import com.management.policy.model.InsuredPerson;
import com.management.policy.model.exception.InsuredPersonNotFoundException;
import com.management.policy.repository.InsuredPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class InsuredPersonService{

    private final InsuredPersonRepository insuredPersonRepository;

    @Autowired
    public InsuredPersonService(InsuredPersonRepository insuredPersonRepository) {
        this.insuredPersonRepository = insuredPersonRepository;
    }

    public InsuredPerson addInsuredPerson(InsuredPerson insuredPerson) {
        return insuredPersonRepository.save(insuredPerson);
    }

    public List<InsuredPerson> getInsuredPersons() {
        return StreamSupport
                .stream(insuredPersonRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public InsuredPerson getInsuredPerson(int id) {
        return insuredPersonRepository.findById(id).orElseThrow( () ->
                new InsuredPersonNotFoundException(id));
    }

    public InsuredPerson deleteInsuredPerson(int id) {
        InsuredPerson insuredPerson = getInsuredPerson(id);
        insuredPersonRepository.delete(insuredPerson);
        return insuredPerson;
    }

    @Transactional
    public InsuredPerson editInsuredPerson(int id, InsuredPerson insuredPerson) {
        InsuredPerson insuredPersonToEdit = getInsuredPerson(id);
        insuredPersonToEdit.setFirstName(insuredPerson.getFirstName());
        insuredPersonToEdit.setSecondName(insuredPerson.getSecondName());
        insuredPersonToEdit.setPremium(insuredPerson.getPremium());
        insuredPersonToEdit.setPolicy(insuredPerson.getPolicy());
        return insuredPersonToEdit;
    }
}

