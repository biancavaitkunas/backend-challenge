package br.com.biancavaitkunas.backend_challenge.validation;

public interface PasswordValidationRule {
    boolean isValid(String password);
}
