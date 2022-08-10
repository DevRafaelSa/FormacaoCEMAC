package com.casamon.formacao.controllers;

import com.casamon.formacao.controllers.DTOs.RespostaDTO;
import com.casamon.formacao.controllers.forms.TextoForm;
import com.casamon.formacao.models.Questao;
import com.casamon.formacao.models.Resposta;
import com.casamon.formacao.repositories.QuestaoRepository;
import com.casamon.formacao.repositories.RespostaRepository;
import com.casamon.formacao.services.MembroService;
import com.casamon.formacao.services.QuestaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/formacoes/{idF}/exercicio/questao/{idQ}")
public class RespostaController {

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private QuestaoRepository questaoRepository;

    @Autowired
    private QuestaoService questaoService;

    @Autowired
    private MembroService membroService;

    @PostMapping("/resposta")
    public ResponseEntity<?> criarResposta (@PathVariable Long idF, @PathVariable Long idQ, @RequestBody TextoForm textoForm){
        Optional<Questao> q = questaoService.encontraQuestao(idF, idQ);
        if (q.isPresent()){
            Resposta r = new Resposta(textoForm.getTexto(),membroService.retornaMembroLogado(),q.get());
            respostaRepository.save(r);
            return new ResponseEntity<>(new RespostaDTO(r), HttpStatus.CREATED);
        }else return new ResponseEntity<>("Essa questão não existe", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/respostas")
    public ResponseEntity<?> listaRespostas(@PathVariable Long idF, @PathVariable Long idQ) {
        Optional<Questao> q = questaoService.encontraQuestao(idF, idQ);
        if (q.isPresent()){
            List<Resposta> listaR = respostaRepository.findAllByQuestaoId(q.get().getId());
            if (!listaR.isEmpty())
                return new ResponseEntity<>(listaR.stream().map(RespostaDTO::new).collect(Collectors.toList()), HttpStatus.OK);
            else return new ResponseEntity<>("Nenhuma resposta encontrada", HttpStatus.NO_CONTENT);
        }else return new ResponseEntity<>("Nenhuma questão encontrada", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/resposta/{idR}")
    public ResponseEntity<?> retornaResposta(@PathVariable Long idF, @PathVariable Long idQ, @PathVariable Long idR){
        Optional<Questao> q = questaoService.encontraQuestao(idF, idQ);
        if (q.isPresent()) {
            Optional<Resposta> r = respostaRepository.findById(idR);
            if(r.isPresent())
                return new ResponseEntity<>(new RespostaDTO(r.get()), HttpStatus.CREATED);
            else return new ResponseEntity<>("Nenhuma resposta encontrada", HttpStatus.NO_CONTENT);
        }else return new ResponseEntity<>("Nenhuma questão encontrada", HttpStatus.NO_CONTENT);

    }

    @PutMapping("/resposta/{idR}")
    @Transactional
    public ResponseEntity<?> atualizaResposta (@PathVariable Long idF, @PathVariable Long idQ, @PathVariable Long idR, @RequestBody TextoForm textoForm){
        Optional<Questao> q = questaoService.encontraQuestao(idF, idQ);
        if (q.isPresent()) {
            Optional<Resposta> r = respostaRepository.findById(idR);
            if(r.isPresent()) {
                Resposta resposta = r.get();
                resposta.setTextoReposta(textoForm.getTexto());
                return new ResponseEntity<>(new RespostaDTO(resposta), HttpStatus.CREATED);
            }else return new ResponseEntity<>("Nenhuma resposta encontrada", HttpStatus.NO_CONTENT);
        }else return new ResponseEntity<>("Nenhuma questão encontrada", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/resposta/{idR}")
    public ResponseEntity<?> deletaFormacao(@PathVariable Long idF, @PathVariable Long idQ, @PathVariable Long idR){
        Optional<Questao> q = questaoService.encontraQuestao(idF, idQ);
        if (q.isPresent()) {
            Optional<Resposta> r = respostaRepository.findById(idR);
            if(r.isPresent()) {
                Resposta resposta = r.get();
                respostaRepository.delete(resposta);
                return new ResponseEntity<>("Deletado com sucesso", HttpStatus.OK);
            }else return new ResponseEntity<>("Nenhuma resposta encontrada", HttpStatus.NO_CONTENT);
        }else return new ResponseEntity<>("Nenhuma questão encontrada", HttpStatus.NO_CONTENT);
    }


}
