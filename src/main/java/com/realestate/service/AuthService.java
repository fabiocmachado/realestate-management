package com.realestate.service;

import com.realestate.dto.AuthResponseDTO;
import com.realestate.dto.LoginDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Person;
import com.realestate.enums.UserRole;
import com.realestate.repository.PersonRepository;
import com.realestate.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        if (person instanceof Agent agent) {
            if (!passwordEncoder.matches(loginDTO.getPassword(), agent.getPassword())) {
                throw new RuntimeException("Senha inválida");
            }

            String role = determineRole(agent);
            String token = jwtTokenProvider.createToken(person.getEmail(), role);

            return AuthResponseDTO.builder()
                    .token(token)
                    .email(person.getEmail())
                    .role(role)
                    .type("Bearer")
                    .name(person.getName())
                    .build();
        } else {
            throw new RuntimeException("A pessoa não é um agente.");
        }
    }

    public String getUserNameByEmail(String email) {
        return personRepository.findByEmail(email)
                .map(Person::getName)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    private String determineRole(Agent agent) {
        return agent.getRole().name();
    }
}
