package org.example.pharmacy.controller;

import org.example.pharmacy.controller.dto.CreateUserRequestDto;
import org.example.pharmacy.controller.dto.CreateUserResponseDto;
import org.example.pharmacy.controller.dto.UserResponseDto;
import org.example.pharmacy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("permitAll()")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "{id}")
    public UserResponseDto getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping
    public CreateUserResponseDto createUser(@Validated @RequestBody CreateUserRequestDto user) {
        return userService.createUser(user);
    }

    @GetMapping("/me")
    public UserResponseDto getMe(Principal principal){
        return new UserResponseDto(1L, principal.getName());
    }
}