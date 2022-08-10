package com.casamon.formacao.controllers.DTOs;

import com.casamon.formacao.models.Resposta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RespostaDTO {
    private Long id;
    private String textoResposta;
    private Long idMembro;
    private Long idQuestao;
    private String feedback;

    public RespostaDTO(Resposta r){
        this.id = r.getId();
        this.textoResposta = r.getTextoReposta();
        this.idMembro = r.getMembro().getId();
        this.idQuestao = r.getQuestao().getId();
        this.feedback = r.getFeedback().getTextoFeedback();

    }
}
