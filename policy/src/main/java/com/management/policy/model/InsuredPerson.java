package com.management.policy.model;

import com.management.policy.model.dto.InsuredPersonDto;
import lombok.Data;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;


@Data
@Entity
@Table(name="insuredPerson")
public class InsuredPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String firstName;
    private String secondName;
    private double premium;
    @ManyToOne
    private Policy policy;

    public static InsuredPerson from(InsuredPersonDto insuredPersonDto){
        InsuredPerson insuredPerson = new InsuredPerson();
        insuredPerson.setId(insuredPersonDto.getId());
        insuredPerson.setFirstName(insuredPersonDto.getFirstName());
        insuredPerson.setSecondName(insuredPersonDto.getSecondName());
        insuredPerson.setPremium(insuredPersonDto.getPremium());
        return insuredPerson;
    }

}
