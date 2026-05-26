package ilya.tsimerman.authservice.Service.impl;

import ilya.tsimerman.authservice.domain.model.UserCredentials;
import ilya.tsimerman.authservice.domain.repository.CredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CredentialsRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserCredentials user = repository.findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException(""));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(Collections.emptyList())
                .build();
    }
}
