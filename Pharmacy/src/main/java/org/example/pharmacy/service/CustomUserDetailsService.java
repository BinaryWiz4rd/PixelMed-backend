package org.example.pharmacy.service;

import org.example.pharmacy.infrastructure.repository.IAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IAuthRepository authRepository;

    @Autowired
    public CustomUserDetailsService(IAuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authRepository.findByUsername(username)
                .map(authEntity -> new CustomUserDetails(
                        authEntity.getUser().getId(),
                        authEntity.getUsername(),
                        authEntity.getPassword(),
                        authEntity.getRole()
                ))
                .orElseThrow(() -> new UsernameNotFoundException("user not found with username: " + username));
    }
}
