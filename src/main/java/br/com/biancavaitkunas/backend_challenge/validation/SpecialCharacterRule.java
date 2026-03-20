package br.com.biancavaitkunas.backend_challenge.validation;

import org.springframework.stereotype.Component;

@Component
public class SpecialCharacterRule implements PasswordValidationRule {
    private static final String SPECIAL_CHARS = "!@#$%^&*()-+";

    @Override
    public boolean isValid(final String password) {
        if (password == null) return false;
        return password.chars().anyMatch(c -> SPECIAL_CHARS.indexOf(c) >= 0);
    }
}