package com.bank.api.exception;

import org.springframework.security.core.AuthenticationException;

public class AccountDisableException extends AuthenticationException {
    public AccountDisableException(String msg, Throwable t) {
        super(msg, t);
    }
    public AccountDisableException(String msg) {
        super(msg);
    }
}
