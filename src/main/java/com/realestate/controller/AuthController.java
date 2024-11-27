package com.realestate.controller;

import com.realestate.dto.AgentDTO;
import com.realestate.dto.AuthResponseDTO;
import com.realestate.dto.LoginDTO;
import com.realestate.exception.BusinessException;
import com.realestate.security.JwtTokenProvider;
import com.realestate.service.AuthService;
import com.realestate.service.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AgentService agentService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        AuthResponseDTO authResponse = authService.login(loginDTO);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/create-first-admin")
    public ResponseEntity<AgentDTO> createFirstAdmin(@Valid @RequestBody AgentDTO agentDTO) {
        try {
            AgentDTO createdAdmin = agentService.createFirstAdmin(agentDTO);
            return ResponseEntity.ok(createdAdmin);
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token não fornecido ou formato inválido.");
        }

        String token = authorizationHeader.substring(7);

        boolean isValid = jwtTokenProvider.validateToken(token);
        if (isValid) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado.");
        }
    }

}
