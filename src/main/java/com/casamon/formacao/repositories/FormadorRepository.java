package com.casamon.formacao.repositories;

import com.casamon.formacao.models.Formador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FormadorRepository extends JpaRepository<Formador,Long> {

    public Boolean existsByEmail(String email);
    public Optional<Formador> findByEmail(String email);
}
