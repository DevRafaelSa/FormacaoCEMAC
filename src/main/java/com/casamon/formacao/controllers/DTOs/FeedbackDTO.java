package com.casamon.formacao.controllers.DTOs;

import com.casamon.formacao.models.Feedback;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FeedbackDTO {
    private Long id;
    private String textoFeedback;
    private Long formadorId;
    private Long respostaId;
//a
    public FeedbackDTO(Feedback f){
        this.id= f.getId();
        this.textoFeedback= f.getTextoFeedback();
        this.formadorId = f.getFormador().getId();
        this.respostaId = f.getResposta().getId();
    }
}
