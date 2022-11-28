package com.management.policy.model.exception;

import java.text.MessageFormat;

public class DateIncorrectException extends RuntimeException {
    public DateIncorrectException(String date) {
        super(MessageFormat.format("This date {0} should be after or equal today", date));
    }
}

