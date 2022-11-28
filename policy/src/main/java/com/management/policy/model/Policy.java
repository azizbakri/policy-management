package com.management.policy.model;

import com.management.policy.model.dto.EffectivePolicyDto;
import com.management.policy.model.dto.InsuredPersonDto;
import com.management.policy.model.dto.PolicyDto;
import com.management.policy.model.dto.RequestPolicyDto;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name="policy")
public class Policy {

    @Id
    private String policyId = randomStringIdGenerator(9) ;
    private String startDate;
    private String effectiveDate;
    private String requestDate;
    private Double totalPremium;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "policy_id")
    private List<InsuredPerson> insuredPersons = new ArrayList<>();

    public void addInsuredPerson(InsuredPerson insuredPerson) { insuredPersons.add(insuredPerson);}
    public void removeInsuredPerson(InsuredPerson insuredPerson) { insuredPersons.remove(insuredPerson);}

    public static Policy from(PolicyDto policyDto) {
        Policy policy = new Policy();
        policy.setStartDate(policyDto.getStartDate());
        policy.setInsuredPersons(policyDto.getInsuredPersonsDto().stream().map(InsuredPerson::from).collect(Collectors.toList()));
        policy.setTotalPremium(policyDto.getInsuredPersonsDto().stream().mapToDouble(o -> o.getPremium()).sum());
        return policy;
    }

    public static Policy from(EffectivePolicyDto effectivePolicyDto) {
        Policy policy = new Policy();
        policy.setEffectiveDate(effectivePolicyDto.getEffectiveDate());
        policy.setInsuredPersons(effectivePolicyDto.getInsuredPersonsDto().stream().map(InsuredPerson::from).collect(Collectors.toList()));
        policy.setTotalPremium(effectivePolicyDto.getInsuredPersonsDto().stream().mapToDouble(o -> o.getPremium()).sum());
        return policy;
    }

    public static Policy from(RequestPolicyDto requestPolicyDto) {
        Policy policy = new Policy();
        policy.setRequestDate(requestPolicyDto.getRequestDate());
        policy.setInsuredPersons(requestPolicyDto.getInsuredPersonsDto().stream().map(InsuredPerson::from).collect(Collectors.toList()));
        policy.setTotalPremium(requestPolicyDto.getInsuredPersonsDto().stream().mapToDouble(o -> o.getPremium()).sum());
        return policy;
    }

    String randomStringIdGenerator(int len){
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

}
