package com.realestate.service;

import com.realestate.security.JwtTokenProvider;
import com.realestate.exception.TokenExpiredException;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;

    public TokenService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void validateTokenExpiration(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new TokenExpiredException("O token expirou. Faça login novamente.");
        }
    }
}
