package com.management.policy.model.dto;

import com.management.policy.model.Policy;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class EffectivePolicyDto {
    private String policyId;
    private String effectiveDate;
    private double totalPremium;
    private List<InsuredPersonDto> insuredPersonsDto = new ArrayList<>();

    public static EffectivePolicyDto from(Policy policy) {
        EffectivePolicyDto effectivePolicyDto = new EffectivePolicyDto();
        effectivePolicyDto.setPolicyId(policy.getPolicyId());
        effectivePolicyDto.setEffectiveDate(policy.getEffectiveDate());
        effectivePolicyDto.setTotalPremium(policy.getTotalPremium());
        effectivePolicyDto.setInsuredPersonsDto(policy.getInsuredPersons().stream().map(InsuredPersonDto::from).collect(Collectors.toList()));
        return effectivePolicyDto;
    }
}
