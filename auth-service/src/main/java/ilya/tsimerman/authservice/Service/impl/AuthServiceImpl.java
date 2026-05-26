package ilya.tsimerman.authservice.Service.impl;

import ilya.tsimerman.authservice.Service.AuthService;
import ilya.tsimerman.authservice.domain.dto.AuthRequest;
import ilya.tsimerman.authservice.domain.model.UserCredentials;
import ilya.tsimerman.authservice.domain.repository.CredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final CredentialsRepository credentialsRepository;

    @Override
    @Transactional
    public void register(AuthRequest request) {

        credentialsRepository.findByEmail(request.email())
                .ifPresent(u -> {
                    throw new BadCredentialsException("Пользователь с таким e-mail уже существует");
                });

        UserCredentials user = new UserCredentials();
        user.setEmail(request.email().trim().toLowerCase());
        user.setPassword(passwordEncoder.encode(request.password()));

        credentialsRepository.save(user);
    }
}