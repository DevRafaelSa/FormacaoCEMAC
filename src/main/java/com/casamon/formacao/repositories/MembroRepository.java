package com.casamon.formacao.repositories;

import com.casamon.formacao.models.Membro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembroRepository extends JpaRepository<Membro,Long> {
    public Boolean existsByEmail(String email);
    public Optional<Membro> findByEmail(String email);
}
