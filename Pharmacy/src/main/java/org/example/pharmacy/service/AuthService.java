package org.example.pharmacy.service;

import org.example.pharmacy.controller.dto.LoginRequestDto;
import org.example.pharmacy.controller.dto.LoginResponseDto;
import org.example.pharmacy.infrastructure.entity.AuthEntity; // Import AuthEntity
import org.example.pharmacy.infrastructure.entity.UserEntity;
import org.example.pharmacy.infrastructure.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final IUserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(IUserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }


    public LoginResponseDto login(LoginRequestDto dto) {
        UserEntity user = userRepository.findByUsername(dto.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));

        var doPasswordMatch = passwordEncoder.matches(dto.getPassword(), user.getPassword());

        if(!doPasswordMatch) {
            throw new RuntimeException("not authorized");
        }
        AuthEntity authEntity = new AuthEntity(); //create auth entity
        authEntity.setUsername(user.getUsername());//set username
        return new LoginResponseDto(jwtService.generateToken(authEntity));
    }
}



/*
package org.example.pharmacy.service;

import org.example.pharmacy.controller.dto.LoginRequestDto;
import org.example.pharmacy.controller.dto.LoginResponseDto;
import org.example.pharmacy.controller.dto.RegisterRequestDto;
import org.example.pharmacy.controller.dto.RegisterResponseDto;
import org.example.pharmacy.infrastructure.entity.AuthEntity;
import org.example.pharmacy.infrastructure.entity.UserEntity;
import org.example.pharmacy.infrastructure.repository.IAuthRepository;
import org.example.pharmacy.infrastructure.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final IAuthRepository authRepository;
    private final IUserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(IAuthRepository authRepository, IUserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponseDto register(RegisterRequestDto dto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(dto.getEmail());
        userRepository.save(userEntity);

        AuthEntity authEntity = new AuthEntity();
        authEntity.setPassword(passwordEncoder.encode(dto.getPassword()));
        authEntity.setUsername(dto.getUsername());
        authEntity.setRole(dto.getRole());
        authEntity.setUser(userEntity);

        authRepository.save(authEntity);

        return new RegisterResponseDto(authEntity.getUsername(), authEntity.getRole(), userEntity.getId());
    }

    public LoginResponseDto login(LoginRequestDto dto) {
        AuthEntity authEntity = authRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(dto.getPassword(), authEntity.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(authEntity);

        return new LoginResponseDto(token);
    }
}*/
