package com.casamon.formacao.repositories;

import com.casamon.formacao.models.Resposta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespostaRepository extends JpaRepository<Resposta,Long> {
    List<Resposta> findAllByQuestaoId(Long id);

}
