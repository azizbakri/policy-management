package com.management.policy.model.dto;

import com.management.policy.model.InsuredPerson;
import lombok.Data;

@Data
public class InsuredPersonDto {
    private int id;
    private double premium;
    private String firstName;
    private String secondName;

    public static InsuredPersonDto from(InsuredPerson insuredPerson) {
        InsuredPersonDto insuredPersonDto = new InsuredPersonDto();
        insuredPersonDto.setId(insuredPerson.getId());
        insuredPersonDto.setFirstName(insuredPerson.getFirstName());
        insuredPersonDto.setSecondName(insuredPerson.getSecondName());
        insuredPersonDto.setPremium(insuredPerson.getPremium());
        return insuredPersonDto;
    }

}
