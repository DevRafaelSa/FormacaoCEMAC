package com.casamon.formacao.controllers;

import com.casamon.formacao.controllers.DTOs.FeedbackDTO;
import com.casamon.formacao.controllers.forms.TextoForm;
import com.casamon.formacao.models.Feedback;
import com.casamon.formacao.models.Resposta;
import com.casamon.formacao.repositories.FeedbackRepository;
import com.casamon.formacao.repositories.QuestaoRepository;
import com.casamon.formacao.repositories.RespostaRepository;
import com.casamon.formacao.services.FormadorService;
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
@RequestMapping("/formacoes/{idF}/exercicio/questao/{idQ}/resposta/{idR}")
public class FeedbackController {

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private QuestaoRepository questaoRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private QuestaoService questaoService;

    @Autowired
    private FormadorService formadorService;

    @PostMapping("/feedback")
    public ResponseEntity<?> criarFeedback (@PathVariable Long idF, @PathVariable Long idQ, @PathVariable Long idR, @RequestBody TextoForm textoForm){
        Optional<Resposta> r = questaoService.encontraResposta(idF, idQ, idR);
        if (r.isPresent()){
            Feedback f = new Feedback(textoForm.getTexto(),formadorService.retornaFormadorLogado(),r.get());
            feedbackRepository.save(f);
            return new ResponseEntity<>(new FeedbackDTO(f), HttpStatus.CREATED);
        }else return new ResponseEntity<>("Essa questão não existe", HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/feedbacks")
    public ResponseEntity<?> listaFeedbacks(@PathVariable Long idF, @PathVariable Long idQ, @PathVariable Long idR) {
        Optional<Resposta> r = questaoService.encontraResposta(idF, idQ, idR);
        if (r.isPresent()){
            List<Feedback> listaF = feedbackRepository.findAllByRespostaId(r.get().getId());
            if (!listaF.isEmpty())
                return new ResponseEntity<>(listaF.stream().map(FeedbackDTO::new).collect(Collectors.toList()), HttpStatus.OK);
            else return new ResponseEntity<>("Nenhum feedback encontrado", HttpStatus.NO_CONTENT);
        }else return new ResponseEntity<>("Nenhuma resposta encontrada", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/feedback/{id}")
    public ResponseEntity<?> retornaFeedback(@PathVariable Long idF,
                                             @PathVariable Long idQ,
                                             @PathVariable Long idR,
                                             @PathVariable Long id){

        Optional<Resposta> r = questaoService.encontraResposta(idF, idQ, idR);
        if (r.isPresent()){
            Optional<Feedback> f = feedbackRepository.findById(id);
            if(f.isPresent())
                return new ResponseEntity<>(new FeedbackDTO(f.get()), HttpStatus.CREATED);
            else return new ResponseEntity<>("Nenhum feedback encontrado", HttpStatus.NO_CONTENT);
        }else return new ResponseEntity<>("Nenhuma resposta encontrada", HttpStatus.NO_CONTENT);

    }

    @PutMapping("/feedback/{id}")
    @Transactional
    public ResponseEntity<?> atualizaResposta (@PathVariable Long idF,
                                               @PathVariable Long idQ,
                                               @PathVariable Long idR,
                                               @PathVariable Long id,
                                               @RequestBody TextoForm textoForm){
        Optional<Resposta> r = questaoService.encontraResposta(idF, idQ, idR);
        if (r.isPresent()){
            Optional<Feedback> f = feedbackRepository.findById(id);
            if(f.isPresent()){
                Feedback feedback = f.get();
                feedback.setTextoFeedback(textoForm.getTexto());
                return new ResponseEntity<>(new FeedbackDTO(feedback), HttpStatus.CREATED);
            }else return new ResponseEntity<>("Nenhum feedback encontrado", HttpStatus.NO_CONTENT);
        }else return new ResponseEntity<>("Nenhuma resposta encontrada", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/feedback/{id}")
    public ResponseEntity<?> deletaFormacao (@PathVariable Long idF,
                                             @PathVariable Long idQ,
                                             @PathVariable Long idR,
                                             @PathVariable Long id,
                                             @RequestBody TextoForm textoForm){
        Optional<Resposta> r = questaoService.encontraResposta(idF, idQ, idR);
        if (r.isPresent()){
            Optional<Feedback> f = feedbackRepository.findById(id);
            if(f.isPresent()){
                Feedback feedback = f.get();
                feedbackRepository.delete(feedback);
                return new ResponseEntity<>("Deletado com sucesso", HttpStatus.OK);
            }else return new ResponseEntity<>("Nenhum feedback encontrado", HttpStatus.NO_CONTENT);
        }else return new ResponseEntity<>("Nenhuma resposta encontrada", HttpStatus.NO_CONTENT);
    }


}
