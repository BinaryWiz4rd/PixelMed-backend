package org.example.pharmacy.service;

import org.example.pharmacy.controller.dto.CreateUserRequestDto;
import org.example.pharmacy.controller.dto.CreateUserResponseDto;
import org.example.pharmacy.controller.dto.UserResponseDto;
import org.example.pharmacy.infrastructure.entity.UserEntity;
import org.example.pharmacy.infrastructure.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public CreateUserResponseDto createUser(CreateUserRequestDto userDto) {
        var userEntity = new UserEntity();

        var hashedPassword = passwordEncoder.encode(userDto.getPassword());

        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(hashedPassword);
        var savedUser = userRepository.save(userEntity);

        return new CreateUserResponseDto(savedUser.getId());
    }

    public UserResponseDto getUser(long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return new UserResponseDto(user.getId(), user.getUsername());
    }
    public UserResponseDto getUserByUsername(String username) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return new UserResponseDto(user.getId(), user.getUsername());
    }
}