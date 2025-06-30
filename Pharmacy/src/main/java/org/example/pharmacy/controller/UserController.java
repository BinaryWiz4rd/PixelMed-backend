package org.example.pharmacy.controller;

import org.example.pharmacy.controller.dto.CreateUserRequestDto;
import org.example.pharmacy.controller.dto.CreateUserResponseDto;
import org.example.pharmacy.controller.dto.UpdateUserRequestDto;
import org.example.pharmacy.controller.dto.UserResponseDto;
import org.example.pharmacy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "{id}")
    @PreAuthorize("permitAll()")
    public UserResponseDto getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping
    @PreAuthorize("permitAll()")
    public CreateUserResponseDto createUser(@Validated @RequestBody CreateUserRequestDto user) {
        return userService.createUser(user);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public UserResponseDto getMe(Principal principal){
        return userService.getUserByUsername(principal.getName());
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated() and #id == principal.id")
    public UserResponseDto updateUser(@PathVariable Long id, @Validated @RequestBody UpdateUserRequestDto userDto, Principal principal) {
        return userService.updateUser(id, userDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated() and #id == principal.id")
    public void deleteUser(@PathVariable Long id, Principal principal) {
        userService.deleteUser(id);
    }
}
