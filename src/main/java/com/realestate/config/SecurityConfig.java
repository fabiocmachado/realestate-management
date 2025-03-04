package com.realestate.config;

import com.realestate.security.JwtAuthenticationFilter;
import com.realestate.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${app.cors.allowed-origins}")
    private List<String> allowedOrigins;

    @Value("${app.environment:dev}")
    private String environment;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/static/**").permitAll()
                .requestMatchers("/index.html").permitAll()
                .requestMatchers("/").permitAll();

        // Se estiver em modo de desenvolvimento, permite mais acessos
        if ("dev".equals(environment)) {
            http.authorizeHttpRequests()
                    .requestMatchers(HttpMethod.GET, "/agents/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/sellers/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/properties/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/properties/**").permitAll()
                    .requestMatchers(HttpMethod.PUT, "/properties/**").permitAll()
                    .requestMatchers(HttpMethod.DELETE, "/properties/**").permitAll();
        } else {
            // Em produção, mantém as restrições de segurança
            http.authorizeHttpRequests()
                    .requestMatchers(HttpMethod.GET, "/agents/**").hasAnyRole("ADMIN", "AGENT")
                    .requestMatchers(HttpMethod.GET, "/sellers/**").hasAnyRole("ADMIN", "AGENT")
                    .requestMatchers(HttpMethod.GET, "/properties/**").hasAnyRole("ADMIN", "AGENT")
                    .requestMatchers(HttpMethod.POST, "/properties/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/properties/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/properties/**").hasRole("ADMIN");
        }

        http.authorizeHttpRequests()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}