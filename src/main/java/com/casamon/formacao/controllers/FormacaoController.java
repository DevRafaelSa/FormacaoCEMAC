package com.casamon.formacao.controllers;

import com.casamon.formacao.controllers.DTOs.FormacaoDTO;
import com.casamon.formacao.controllers.forms.FormacaoForm;
import com.casamon.formacao.models.Formacao;
import com.casamon.formacao.repositories.ExercicioRepository;
import com.casamon.formacao.repositories.FormacaoRepository;
import com.casamon.formacao.services.FormadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/formacoes")
public class FormacaoController {

    @Autowired
    private FormacaoRepository formacaoRepository;

    @Autowired
    private ExercicioRepository exercicioRepository;

    @Autowired
    private FormadorService formadorService;


    @PostMapping("/criar")
    public ResponseEntity<?> criarFormacao (@RequestBody FormacaoForm formacaoForm){
        if(!formacaoRepository.existsByNumero(formacaoForm.getNumero())){
            Formacao f = new Formacao(
                    formacaoForm.getNumero(),
                    formacaoForm.getLinkMaterial(),
                    formadorService.retornaFormadorLogado()
                    );
            formacaoRepository.save(f);

            return new ResponseEntity<>(new FormacaoDTO(f), HttpStatus.CREATED);
        }else return new ResponseEntity<>("Essa formação já existe", HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<?> listaFormacoesDoFormador(){
        List<Formacao> listaF = formacaoRepository.findAllByFormadorId(formadorService.retornaFormadorLogado().getId());
        if(!listaF.isEmpty()) return new ResponseEntity<>(listaF.stream().map(FormacaoDTO::new).collect(Collectors.toList()), HttpStatus.OK);
        else return new ResponseEntity<>("Nenhuma formação encontrada", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> retornaFormacao(@PathVariable Long id){
        Formacao f = formacaoRepository.findById(id).orElseThrow(() -> new NullPointerException("Não foi encontrada formacao com id = " + id));
        return new ResponseEntity<>(new FormacaoDTO(f), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizaFormacao (@PathVariable Long id, @RequestBody FormacaoForm formacaoForm){
        if(!formacaoRepository.existsById(id)) return new ResponseEntity<>("Não foi encontrada formacao com id = " + id, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(new FormacaoDTO(formacaoForm.atualizar(id, formacaoRepository)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletaFormacao(@PathVariable Long id){
        Formacao f = formacaoRepository.findById(id).orElseThrow(() -> new NullPointerException("Não foi encontrada formacao com id = " + id));
        formacaoRepository.delete(f);
        return new ResponseEntity<>("Deletado com sucesso", HttpStatus.OK);
    }


}
