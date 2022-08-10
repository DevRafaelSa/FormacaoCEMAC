package com.casamon.formacao.repositories;

import com.casamon.formacao.models.Formacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormacaoRepository extends JpaRepository<Formacao,Long> {
    boolean existsByNumero(int numero);

    List<Formacao> findAllByFormadorId(Long id);
}
