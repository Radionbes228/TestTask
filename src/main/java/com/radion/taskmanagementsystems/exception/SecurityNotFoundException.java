package com.radion.taskmanagementsystems.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SecurityNotFoundException extends UsernameNotFoundException {
    public SecurityNotFoundException(String msg) {
        super(msg);
    }
}
