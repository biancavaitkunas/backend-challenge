package br.com.biancavaitkunas.backend_challenge.controller;

import br.com.biancavaitkunas.backend_challenge.service.PasswordValidationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PasswordValidationController.class)
class PasswordValidationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PasswordValidationService service;

    @Test
    @DisplayName("Deve retornar status 200 e isValid true quando a requisição for válida")
    void deveRetornar200ETrueQuandoSenhaValida() throws Exception {
        Mockito.when(service.validate("AbTp9!fok")).thenReturn(true);

        final var jsonRequest = "{\"password\": \"AbTp9!fok\"}";

        mockMvc.perform(post("/api/v1/passwords/validation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isValid").value(true));
    }
}
