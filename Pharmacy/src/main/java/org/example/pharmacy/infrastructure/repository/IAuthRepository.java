package org.example.pharmacy.infrastructure.repository;

import org.example.pharmacy.infrastructure.entity.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IAuthRepository extends JpaRepository<AuthEntity, Long> {
    Optional<AuthEntity> findByUsername(String username);

}