package com.management.policy.model.dto;

import com.management.policy.model.Policy;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class RequestPolicyDto {
    private String policyId;
    private String requestDate;
    private double totalPremium;
    private List<InsuredPersonDto> insuredPersonsDto = new ArrayList<>();

    public static RequestPolicyDto from(Policy policy) {
        RequestPolicyDto requestPolicyDto = new RequestPolicyDto();
        requestPolicyDto.setPolicyId(policy.getPolicyId());
        requestPolicyDto.setRequestDate(policy.getRequestDate());
        requestPolicyDto.setTotalPremium(policy.getTotalPremium());
        requestPolicyDto.setInsuredPersonsDto(policy.getInsuredPersons().stream().map(InsuredPersonDto::from).collect(Collectors.toList()));
        return requestPolicyDto;
    }
}
