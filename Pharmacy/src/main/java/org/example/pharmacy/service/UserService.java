package org.example.pharmacy.service;

import org.example.pharmacy.commonTypes.UserRole;
import org.example.pharmacy.controller.dto.CreateUserRequestDto;
import org.example.pharmacy.controller.dto.CreateUserResponseDto;
import org.example.pharmacy.controller.dto.UpdateUserRequestDto;
import org.example.pharmacy.controller.dto.UserResponseDto;
import org.example.pharmacy.infrastructure.entity.AuthEntity;
import org.example.pharmacy.infrastructure.entity.UserEntity;
import org.example.pharmacy.infrastructure.repository.IAuthRepository;
import org.example.pharmacy.infrastructure.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final IUserRepository userRepository;
    private final IAuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserRepository userRepository, IAuthRepository authRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authRepository = authRepository;
    }

    @Transactional
    public CreateUserResponseDto createUser(CreateUserRequestDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent() ||
                authRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new RuntimeException("username already exists.");
        }

        var userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        var savedUser = userRepository.save(userEntity);

        var authEntity = new AuthEntity();
        authEntity.setUsername(userDto.getUsername());
        authEntity.setPassword(savedUser.getPassword());
        authEntity.setRole(UserRole.ROLE_PHARMACIST);
        authEntity.setUser(savedUser);

        authRepository.save(authEntity);

        return new CreateUserResponseDto(savedUser.getId());
    }

    public UserResponseDto getUser(long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found."));
        return new UserResponseDto(user.getId(), user.getUsername());
    }

    public UserResponseDto getUserByUsername(String username) {
        var authEntity = authRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("user not found."));
        return new UserResponseDto(authEntity.getUser().getId(), authEntity.getUsername());
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UpdateUserRequestDto userDto) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("user not found."));

        AuthEntity authEntity = authRepository.findByUsername(userEntity.getUsername())
                .orElseThrow(() -> new RuntimeException("auth record not found for user."));

        boolean updated = false;

        if (userDto.getUsername() != null && !userDto.getUsername().isEmpty() && !userDto.getUsername().equals(userEntity.getUsername())) {
            if (userRepository.findByUsername(userDto.getUsername()).isPresent() ||
                    authRepository.findByUsername(userDto.getUsername()).isPresent()) {
                throw new RuntimeException("new username already exists.");
            }
            userEntity.setUsername(userDto.getUsername());
            authEntity.setUsername(userDto.getUsername());
            updated = true;
        }

        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(userDto.getPassword());
            userEntity.setPassword(encodedPassword);
            authEntity.setPassword(encodedPassword);
            updated = true;
        }

        if (updated) {
            userRepository.save(userEntity);
            authRepository.save(authEntity);
        }

        return new UserResponseDto(userEntity.getId(), userEntity.getUsername());
    }

    @Transactional
    public void deleteUser(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("user not found."));

        AuthEntity authEntity = authRepository.findByUsername(userEntity.getUsername())
                .orElseThrow(() -> new RuntimeException("auth record not found for user."));

        authRepository.delete(authEntity);
        userRepository.delete(userEntity);
    }
}
