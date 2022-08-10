package com.casamon.formacao.controllers.DTOs;

import com.casamon.formacao.models.Questao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuestaoDTO {
    private Long id;
    private String texto;
    private Long idExercicio;
    private Long idFormacao;

    public QuestaoDTO(Questao q){
        this.id = q.getId();
        this.texto = q.getTexto();
        this.idExercicio = q.getExercicio().getId();
        this.idFormacao = q.getExercicio().getFormacao().getId();

    }
}
