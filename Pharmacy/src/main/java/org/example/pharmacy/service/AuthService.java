package org.example.pharmacy.service;

import org.example.pharmacy.controller.dto.LoginRequestDto;
import org.example.pharmacy.controller.dto.LoginResponseDto;
import org.example.pharmacy.infrastructure.entity.AuthEntity;
import org.example.pharmacy.infrastructure.repository.IAuthRepository;
import org.example.pharmacy.infrastructure.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
    private final IAuthRepository authRepository;
    private final IUserRepository userRepository; //na potem
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder; //na potem np reset hasla
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(IAuthRepository authRepository, IUserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public LoginResponseDto login(LoginRequestDto dto) {
        AuthEntity authEntity = authRepository.findByUsername(dto.getUsername())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "not authorized"));

        var passwordMatch = passwordEncoder.matches(dto.getPassword(), authEntity.getPassword());
            if (!passwordMatch) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "not authorized");
            }

            try{
                var token = jwtService.generateToken(authEntity);
                return new LoginResponseDto(token);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
            }
   }
} 
