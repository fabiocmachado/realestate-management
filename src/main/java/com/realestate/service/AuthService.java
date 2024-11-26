package com.realestate.service;

import com.realestate.dto.AuthResponseDTO;
import com.realestate.dto.LoginDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Person;
import com.realestate.enums.UserRole;
import com.realestate.repository.PersonRepository;
import com.realestate.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthResponseDTO login(LoginDTO loginDTO) {
        Person person = personRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), person.getPassword())) {
            throw new RuntimeException("Senha inválida");
        }

        String role = determineRole(person);
        String token = jwtTokenProvider.createToken(person.getEmail(), role);

        return AuthResponseDTO.builder()
                .token(token)
                .email(person.getEmail())
                .role(UserRole.valueOf(role))
                .type("Bearer")
                .build();
    }

    private String determineRole(Person person) {
        if (person instanceof Agent) {
            Agent agent = (Agent) person;
            return agent.getHasAdminPermissions() ? "ADMIN" : "AGENT";
        }
        return "AGENT";
    }
}
