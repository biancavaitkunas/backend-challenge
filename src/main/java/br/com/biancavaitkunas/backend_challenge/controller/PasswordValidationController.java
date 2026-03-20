package br.com.biancavaitkunas.backend_challenge.controller;

import br.com.biancavaitkunas.backend_challenge.dto.PasswordValidationRequest;
import br.com.biancavaitkunas.backend_challenge.dto.PasswordValidationResponse;
import br.com.biancavaitkunas.backend_challenge.service.PasswordValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/passwords")
public class PasswordValidationController {

    private final PasswordValidationService passwordValidationService;

    public PasswordValidationController(PasswordValidationService passwordValidationService) {
        this.passwordValidationService = passwordValidationService;
    }

    @PostMapping("/validation")
    public ResponseEntity<PasswordValidationResponse> validatePassword(final @RequestBody PasswordValidationRequest request) {
        final var isValid = passwordValidationService.validate(request.password());
        return ResponseEntity.ok(new PasswordValidationResponse(isValid));
    }
}
