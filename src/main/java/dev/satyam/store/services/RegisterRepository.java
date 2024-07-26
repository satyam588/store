package dev.satyam.store.services;

import dev.satyam.store.models.Register;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegisterRepository extends JpaRepository<Register, Integer> {
    Optional<Register> findByEmail(String email);
//    Optional<Register> findByEmailPassword(String email, Long password);
}
