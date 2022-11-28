package com.management.policy.model.dto;

import com.management.policy.model.Policy;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PolicyDto {
    private String policyId;
    private String startDate;
    private double totalPremium;
    private List<InsuredPersonDto> insuredPersonsDto = new ArrayList<>();

    public static PolicyDto from(Policy policy) {
        PolicyDto policyDto = new PolicyDto();
        policyDto.setPolicyId(policy.getPolicyId());
        policyDto.setStartDate(policy.getStartDate());
        policyDto.setTotalPremium(policy.getTotalPremium());
        policyDto.setInsuredPersonsDto(policy.getInsuredPersons().stream().map(InsuredPersonDto::from).collect(Collectors.toList()));
        return policyDto;
    }
}
