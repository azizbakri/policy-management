package com.management.policy.model.exception;

import java.text.MessageFormat;

public class PolicyNotFoundException extends RuntimeException{
    public PolicyNotFoundException(String policyId) {
        super(MessageFormat.format("Could not find Policy with id: {0}", policyId));
    }
}
