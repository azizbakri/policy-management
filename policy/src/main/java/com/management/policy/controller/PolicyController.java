package com.management.policy.controller;

import com.management.policy.model.Policy;
import com.management.policy.model.dto.EffectivePolicyDto;
import com.management.policy.model.dto.PolicyDto;
import com.management.policy.model.dto.RequestPolicyDto;
import com.management.policy.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/policy")
public class PolicyController {
    private final PolicyService policyService;

    @Autowired
    public PolicyController(PolicyService policyService) {this.policyService = policyService;}

    @PostMapping()
    public ResponseEntity<PolicyDto> createPolicy(@RequestBody final PolicyDto policyDto) {
        Policy policy = policyService.create(Policy.from(policyDto));
        return new ResponseEntity<>(PolicyDto.from(policy), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<PolicyDto>> getPolicies() {
        List<Policy> policies = policyService.getPolicies();
        List<PolicyDto> policiesDto = policies.stream().map(PolicyDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(policiesDto, HttpStatus.OK);
    }

    @GetMapping(value = "{policyId}")
    public ResponseEntity<PolicyDto> getPolicy(@PathVariable final String policyId) {
        Policy policy = policyService.getPolicy(policyId);
        return new ResponseEntity<>(PolicyDto.from(policy), HttpStatus.OK);
    }

    @PostMapping(value = "{policyId}")
    public ResponseEntity<RequestPolicyDto> getPolicyByRequestDate(@PathVariable final String policyId,
                                                                   @RequestBody RequestPolicyDto requestPolicyDto) {
        Policy policy = policyService.getPolicyByRequestDate(policyId, Policy.from(requestPolicyDto));
        return new ResponseEntity<>(RequestPolicyDto.from(policy), HttpStatus.OK);
    }

    @DeleteMapping(value = "{policyId}")
    public ResponseEntity<PolicyDto> deletePolicy(@PathVariable final String policyId) {
        Policy policy = policyService.deletePolicy(policyId);
        return new ResponseEntity<>(PolicyDto.from(policy), HttpStatus.OK);
    }

    @PutMapping(value = "{policyId}")
    public ResponseEntity<EffectivePolicyDto> editPolicy(@PathVariable final String policyId,
                                                  @RequestBody EffectivePolicyDto effectivePolicyDto) {
        Policy policy = policyService.editPolicy(policyId, Policy.from(effectivePolicyDto));
        return new ResponseEntity<>(EffectivePolicyDto.from(policy), HttpStatus.OK);
    }

    @PostMapping(value = "{policyId}/insuredpersons/{insuredPersonId}/add")
    public ResponseEntity<PolicyDto> addInsuredPersonToPolicy(@PathVariable final String policyId,
                                                              @PathVariable final int insuredPersonId) {
        Policy policy = policyService.addInsuredPersonToPolicy(policyId, insuredPersonId);
        return new ResponseEntity<>(PolicyDto.from(policy), HttpStatus.OK);
    }

    @DeleteMapping(value = "{policyId}/insuredpersons/{insuredPersonId}/delete")
    public ResponseEntity<PolicyDto> deleteInsuredPersonToPolicy(@PathVariable final String policyId,
                                                              @PathVariable final int insuredPersonId) {
        Policy policy = policyService.removeInsuredPersonToPolicy(policyId, insuredPersonId);
        return new ResponseEntity<>(PolicyDto.from(policy), HttpStatus.OK);
    }
}
