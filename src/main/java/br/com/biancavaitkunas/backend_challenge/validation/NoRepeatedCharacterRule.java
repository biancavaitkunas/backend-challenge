package br.com.biancavaitkunas.backend_challenge.validation;

import org.springframework.stereotype.Component;

@Component
public class NoRepeatedCharacterRule implements PasswordValidationRule {

    @Override
    public boolean isValid(final String password) {
        if (password == null) return false;
        var uniqueCharactersCount = password.chars().distinct().count();
        return uniqueCharactersCount == password.length();
    }

}
