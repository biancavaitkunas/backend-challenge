package br.com.biancavaitkunas.backend_challenge.service;

import br.com.biancavaitkunas.backend_challenge.validation.*;
import br.com.biancavaitkunas.backend_challenge.validation.PasswordValidationRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordValidationServiceTest {

    private PasswordValidationService service;

    @BeforeEach
    void setUp() {
        List<PasswordValidationRule> rules = List.of(
                new LengthRule(),
                new DigitRule(),
                new LowercaseRule(),
                new UppercaseRule(),
                new SpecialCharacterRule(),
                new NoRepeatedCharacterRule(),
                new NoWhitespaceRule()
        );
        service = new PasswordValidationService(rules);
    }

    @Test
    @DisplayName("Deve retornar TRUE para uma senha perfeitamente válida")
    void deveRetornarTrueParaSenhaValida() {
        final var isValid = service.validate("AbTp9!fok");
        assertTrue(isValid);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "", "aa", "ab", "AAAbbbCc", "AbTp9!foo", "AbTp9!fo k", "AbTp9 fok"
    })
    @DisplayName("Deve retornar FALSE para senhas inválidas conhecidas")
    void deveRetornarFalseParaSenhasInvalidas(final String invalidPassword) {
        final var isValid = service.validate(invalidPassword);
        assertFalse(isValid);
    }

    @Test
    @DisplayName("Deve retornar FALSE quando a senha for nula")
    void deveRetornarFalseParaSenhaNula() {
        final var isValid = service.validate(null);
        assertFalse(isValid);
    }
}
