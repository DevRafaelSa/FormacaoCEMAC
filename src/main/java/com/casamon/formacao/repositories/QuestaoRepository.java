package com.casamon.formacao.repositories;

import com.casamon.formacao.models.Questao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestaoRepository extends JpaRepository<Questao,Long> {
    List<Questao> findAllByExercicioId(Long id);
}
