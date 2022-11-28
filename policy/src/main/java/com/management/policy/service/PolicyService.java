package com.management.policy.service;

import com.management.policy.model.InsuredPerson;
import com.management.policy.model.Policy;
import com.management.policy.model.exception.DateIncorrectException;
import com.management.policy.model.exception.InsuredPersonAlreadyAssignedException;
import com.management.policy.model.exception.PolicyNotFoundException;
import com.management.policy.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PolicyService{

    public final PolicyRepository policyRepository;
    public final InsuredPersonService insuredPersonService;

    @Autowired
    public PolicyService(PolicyRepository policyRepository, InsuredPersonService insuredPersonService) {
        this.policyRepository = policyRepository;
        this.insuredPersonService = insuredPersonService;
    }

    public Policy create(Policy policy) {
        policy.setTotalPremium(policy.getInsuredPersons().stream().mapToDouble(o->o.getPremium()).sum());
        isDateCorrect(policy.getStartDate());
        return policyRepository.save(policy);
    }

    public List<Policy> getPolicies() {
        return StreamSupport
                .stream(policyRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Policy getPolicy(String policyId) {
        return policyRepository.findById(policyId).orElseThrow( () ->
                new PolicyNotFoundException(policyId));
    }

    @Transactional
    public Policy editPolicy(String policyId, Policy policy) {
        Policy policyToEdit = getPolicy(policyId);
        policyToEdit.setStartDate(policy.getStartDate());
        policyToEdit.setRequestDate(policy.getRequestDate());
        policyToEdit.setEffectiveDate(policy.getEffectiveDate());

        //add new insuredPersons and edit existing ones and remove the existings ones that are not contained in the new policy
        Set<Integer> ids = policy.getInsuredPersons().stream().map(obj -> obj.getId()).collect(Collectors.toSet());
        policyToEdit.getInsuredPersons().removeIf(obj -> !ids.contains(obj.getId()));
        for (InsuredPerson insuredPerson: policy.getInsuredPersons()) {
            if (insuredPerson.getId()!=0) {
                InsuredPerson insuredPersonToEdit = insuredPersonService.getInsuredPerson(insuredPerson.getId());
                insuredPersonToEdit.setPremium(insuredPerson.getPremium());
                insuredPersonToEdit.setFirstName(insuredPerson.getFirstName());
                insuredPersonToEdit.setSecondName(insuredPerson.getSecondName());
            } else {
                policyToEdit.addInsuredPerson(insuredPerson);
            }
        }

        policyToEdit.setTotalPremium(policy.getInsuredPersons().stream().mapToDouble(o->o.getPremium()).sum());
        return policyToEdit;
    }

    public Policy deletePolicy(String policyId) {
        Policy policy = getPolicy(policyId);
        policyRepository.delete(policy);
        return policy;
    }

    @Transactional
    public Policy addInsuredPersonToPolicy(String policyId, int id) {
        Policy policy = getPolicy(policyId);
        InsuredPerson insuredPerson = insuredPersonService.getInsuredPerson(id);
        if (Objects.nonNull(insuredPerson.getPolicy()))
            throw new InsuredPersonAlreadyAssignedException(id, policyId);
        policy.addInsuredPerson(insuredPerson);
        return policy;
    }

    @Transactional
    public Policy removeInsuredPersonToPolicy(String policyId, int id) {
        Policy policy = getPolicy(policyId);
        InsuredPerson insuredPerson = insuredPersonService.getInsuredPerson(id);
        policy.removeInsuredPerson(insuredPerson);
        return policy;
    }

    public boolean isDateCorrect (String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate dateFormatted = LocalDate.parse(date, formatter);
        if (dateFormatted.isBefore(LocalDate.now()))
            throw new DateIncorrectException(dateFormatted.toString());
        return true;
    }


    public Policy getPolicyByRequestDate(String policyId, Policy policy) {
        Policy policyToGet = getPolicy(policyId);
        String date = policy.getRequestDate();
        if (Objects.nonNull(date)) {
            isDateCorrect(date);
            policyToGet.setRequestDate(date);
        }
        else {
             policyToGet.setRequestDate(
                     LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
             );
        }
        return policyToGet;
    }
}
