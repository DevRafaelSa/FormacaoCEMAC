package com.casamon.formacao.repositories;

import com.casamon.formacao.models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
    List<Feedback> findAllByRespostaId(Long id);
}
