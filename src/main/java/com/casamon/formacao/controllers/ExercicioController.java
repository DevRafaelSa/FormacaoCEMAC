package com.casamon.formacao.controllers;

import com.casamon.formacao.controllers.DTOs.QuestaoDTO;
import com.casamon.formacao.controllers.forms.TextoForm;
import com.casamon.formacao.models.Questao;
import com.casamon.formacao.repositories.FormacaoRepository;
import com.casamon.formacao.repositories.QuestaoRepository;
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
@RequestMapping("/formacoes/{id}/exercicio")
public class ExercicioController {

    @Autowired
    private FormacaoRepository formacaoRepository;

    @Autowired
    private QuestaoRepository questaoRepository;

    @Autowired
    private QuestaoService questaoService;

    @PostMapping("/criarQuestao")
    public ResponseEntity<?> criarQuestao (@PathVariable Long id, @RequestBody TextoForm textoForm){
        if(formacaoRepository.existsById(id)){
            Questao q = new Questao(textoForm.getTexto(), formacaoRepository.findById(id).get().getExercicio());
            questaoRepository.save(q);
            return new ResponseEntity<>(new QuestaoDTO(q), HttpStatus.CREATED);
        }else return new ResponseEntity<>("Essa formação não existe", HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<?> listaQuestoes(@PathVariable Long id) {
        if (formacaoRepository.existsById(id)) {
            List<Questao> listaQ = questaoRepository.findAllByExercicioId(formacaoRepository.findById(id).get().getExercicio().getId());
            if (!listaQ.isEmpty())
                return new ResponseEntity<>(listaQ.stream().map(QuestaoDTO::new).collect(Collectors.toList()), HttpStatus.OK);
            else return new ResponseEntity<>("Nenhuma questão encontrada", HttpStatus.NO_CONTENT);
        }else return new ResponseEntity<>("Nenhuma formacao encontrada", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/questao/{idQuestao}")
    public ResponseEntity<?> retornaQuestao(@PathVariable Long id, @PathVariable Long idQuestao){
        Optional<Questao> q = questaoService.encontraQuestao(id, idQuestao);
        if (q.isPresent())
            return new ResponseEntity<>(new QuestaoDTO(q.get()), HttpStatus.CREATED);
        else return new ResponseEntity<>("Nenhuma questão encontrada", HttpStatus.NO_CONTENT);

    }

    @PutMapping("/questao/{idQuestao}")
    @Transactional
    public ResponseEntity<?> atualizaQuestao (@PathVariable Long id, @PathVariable Long idQuestao,  @RequestBody TextoForm textoForm){
        Optional<Questao> q = questaoService.encontraQuestao(id, idQuestao);
        if (q.isPresent()){
                Questao questao = q.get();
                questao.setTexto(textoForm.getTexto());
                return new ResponseEntity<>(new QuestaoDTO(questao), HttpStatus.CREATED);
            }else return new ResponseEntity<>("Nenhuma questão encontrada", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/questao/{idQuestao}")
    public ResponseEntity<?> deletaFormacao(@PathVariable Long id, @PathVariable Long idQuestao){
        Optional<Questao> q = questaoService.encontraQuestao(id, idQuestao);
        if (q.isPresent()){
            Questao questao = q.get();
                questaoRepository.delete(questao);
                return new ResponseEntity<>("Deletado com sucesso", HttpStatus.OK);
            }else return new ResponseEntity<>("Nenhuma questão encontrada", HttpStatus.NO_CONTENT);
    }


}
