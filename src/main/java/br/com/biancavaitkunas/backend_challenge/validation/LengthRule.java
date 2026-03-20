package br.com.biancavaitkunas.backend_challenge.validation;

import org.springframework.stereotype.Component;

@Component
public class LengthRule implements PasswordValidationRule {

    @Override
    public boolean isValid(final String password) {
        return password != null && password.length() >= 9;
    }

}
