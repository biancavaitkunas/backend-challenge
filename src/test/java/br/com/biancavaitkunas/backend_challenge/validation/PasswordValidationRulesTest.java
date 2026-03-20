package br.com.biancavaitkunas.backend_challenge.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testes Unitários das Regras de Validação")
class PasswordValidationRulesTest {

    @Nested
    @DisplayName("Regra: Mínimo de 9 caracteres")
    class LengthRuleTest {
        private final LengthRule rule = new LengthRule();

        @Test
        void devePassarQuandoTem9OuMaisCaracteres() {
            assertTrue(rule.isValid("123456789"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "12345678"})
        void deveFalharQuandoTemMenosDe9Caracteres(String senha) {
            assertFalse(rule.isValid(senha));
        }

        @Test
        void deveFalharQuandoNulo() {
            assertFalse(rule.isValid(null));
        }
    }

    @Nested
    @DisplayName("Regra: Pelo menos 1 dígito")
    class DigitRuleTest {
        private final DigitRule rule = new DigitRule();

        @Test
        void devePassarQuandoTemDigito() {
            assertTrue(rule.isValid("Senha1"));
        }

        @Test
        void deveFalharQuandoNaoTemDigito() {
            assertFalse(rule.isValid("SenhaSemNumero"));
        }

        @Test
        void deveFalharQuandoNulo() {
            assertFalse(rule.isValid(null));
        }
    }

    @Nested
    @DisplayName("Regra: Pelo menos 1 letra minúscula")
    class LowercaseRuleTest {
        private final LowercaseRule rule = new LowercaseRule();

        @Test
        void devePassarQuandoTemMinuscula() {
            assertTrue(rule.isValid("SENHAa"));
        }

        @Test
        void deveFalharQuandoNaoTemMinuscula() {
            assertFalse(rule.isValid("SENHASOMAIUSCULA1!"));
        }

        @Test
        void deveFalharQuandoNulo() {
            assertFalse(rule.isValid(null));
        }
    }

    @Nested
    @DisplayName("Regra: Pelo menos 1 letra maiúscula")
    class UppercaseRuleTest {
        private final UppercaseRule rule = new UppercaseRule();

        @Test
        void devePassarQuandoTemMaiuscula() {
            assertTrue(rule.isValid("senhaA"));
        }

        @Test
        void deveFalharQuandoNaoTemMaiuscula() {
            assertFalse(rule.isValid("senhasominuscular1!"));
        }

        @Test
        void deveFalharQuandoNulo() {
            assertFalse(rule.isValid(null));
        }
    }

    @Nested
    @DisplayName("Regra: Pelo menos 1 caractere especial")
    class SpecialCharacterRuleTest {
        private final SpecialCharacterRule rule = new SpecialCharacterRule();

        @ParameterizedTest
        @ValueSource(strings = {"senha!", "senha@", "senha#", "senha$", "senha%", "senha^", "senha&", "senha*", "senha(", "senha)", "senha-", "senha+"})
        void devePassarQuandoTemCaractereEspecialValido(String senha) {
            assertTrue(rule.isValid(senha));
        }

        @Test
        void deveFalharQuandoNaoTemCaractereEspecial() {
            assertFalse(rule.isValid("SenhaSemEspecial1"));
        }

        @Test
        void deveFalharQuandoNulo() {
            assertFalse(rule.isValid(null));
        }
    }

    @Nested
    @DisplayName("Regra: Sem caracteres repetidos")
    class NoRepeatedCharacterRuleTest {
        private final NoRepeatedCharacterRule rule = new NoRepeatedCharacterRule();

        @Test
        void devePassarQuandoNaoTemRepetidos() {
            assertTrue(rule.isValid("AbTp9!fok"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"aab", "aba", "baa", "Senha11", "SSenha1!"})
        void deveFalharQuandoTemRepetidos(String senha) {
            assertFalse(rule.isValid(senha));
        }

        @Test
        void deveFalharQuandoNulo() {
            assertFalse(rule.isValid(null));
        }
    }

    @Nested
    @DisplayName("Regra: Sem espaços em branco")
    class NoWhitespaceRuleTest {
        private final NoWhitespaceRule rule = new NoWhitespaceRule();

        @Test
        void devePassarQuandoNaoTemEspaco() {
            assertTrue(rule.isValid("SenhaSemEspaco1!"));
        }

        @ParameterizedTest
        @ValueSource(strings = {" Senha1!", "Senha 1!", "Senha1! "})
        void deveFalharQuandoTemEspaco(String senha) {
            assertFalse(rule.isValid(senha));
        }

        @Test
        void deveFalharQuandoNulo() {
            assertFalse(rule.isValid(null));
        }
    }
}
