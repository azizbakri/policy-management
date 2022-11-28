package com.management.policy.repository;

import com.management.policy.model.Policy;
import org.springframework.data.repository.CrudRepository;

public interface PolicyRepository extends CrudRepository<Policy, String> {

}
