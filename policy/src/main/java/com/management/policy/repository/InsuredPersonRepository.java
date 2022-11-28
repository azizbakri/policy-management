package com.management.policy.repository;

import com.management.policy.model.InsuredPerson;
import org.springframework.data.repository.CrudRepository;

public interface InsuredPersonRepository extends CrudRepository<InsuredPerson, Integer> {
}
