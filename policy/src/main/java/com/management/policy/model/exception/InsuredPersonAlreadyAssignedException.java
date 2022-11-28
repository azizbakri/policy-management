package com.management.policy.model.exception;

import java.text.MessageFormat;

public class InsuredPersonAlreadyAssignedException extends RuntimeException {
    public InsuredPersonAlreadyAssignedException(final int insuredPersonId, final String policyId) {
        super(MessageFormat.format("Insured Person with id {0] is already assigned to Policy with policy id {1}", insuredPersonId, policyId));
    }
}
