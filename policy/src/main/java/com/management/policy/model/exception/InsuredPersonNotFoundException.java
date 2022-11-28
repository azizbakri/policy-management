package com.management.policy.model.exception;

import java.text.MessageFormat;

public class InsuredPersonNotFoundException extends RuntimeException{
    public InsuredPersonNotFoundException(int id) {
        super(MessageFormat.format("Could not find InsuredPerson with id: {0}", id));
    }
}
