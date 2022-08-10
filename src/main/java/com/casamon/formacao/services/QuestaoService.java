package com.casamon.formacao.services;

import com.casamon.formacao.models.Questao;
import com.casamon.formacao.models.Resposta;
import com.casamon.formacao.repositories.FormacaoRepository;
import com.casamon.formacao.repositories.QuestaoRepository;
import com.casamon.formacao.repositories.RespostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestaoService {

    @Autowired
    private FormacaoRepository formacaoRepository;

    @Autowired
    private QuestaoRepository questaoRepository;

    @Autowired
    private RespostaRepository respostaRepository;


    public Optional<Questao> encontraQuestao(Long idFormacao, Long idQuestao) {
        if (formacaoRepository.existsById(idFormacao)) {
            return questaoRepository.findById(idQuestao);
        } else return null;
    }

    public Optional<Resposta> encontraResposta(Long idFormacao, Long idQuestao, Long idResposta) {
        if (formacaoRepository.existsById(idFormacao)) {
            if(questaoRepository.existsById(idQuestao)){
                return respostaRepository.findById(idResposta);
            }else return null;
        } else return null;
    }
}
