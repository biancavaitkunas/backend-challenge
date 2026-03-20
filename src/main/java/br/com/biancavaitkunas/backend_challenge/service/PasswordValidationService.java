package br.com.biancavaitkunas.backend_challenge.service;

import br.com.biancavaitkunas.backend_challenge.validation.PasswordValidationRule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasswordValidationService {

    private final List<PasswordValidationRule> rules;

    public PasswordValidationService(List<PasswordValidationRule> rules) {
        this.rules = rules;
    }

    public boolean validate(final String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }

        return rules.stream().allMatch(rule -> rule.isValid(password));
    }
}
