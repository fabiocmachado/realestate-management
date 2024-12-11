package com.realestate.service;

import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Person;
import com.realestate.repository.PersonRepository;
import com.realestate.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    public CustomUserDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person person = personRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        if (person instanceof Agent) {
            Agent agent = (Agent) person;
            return new CustomUserDetails(agent, person);
        } else {
            return new CustomUserDetails(null, person);
        }
    }
}

