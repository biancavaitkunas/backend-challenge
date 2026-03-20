package br.com.biancavaitkunas.backend_challenge.validation;

import org.springframework.stereotype.Component;

@Component
public class DigitRule implements PasswordValidationRule {

    @Override
    public boolean isValid(final String password) {
        if (password == null) return false;
        return password.chars().anyMatch(Character::isDigit);
    }

}
