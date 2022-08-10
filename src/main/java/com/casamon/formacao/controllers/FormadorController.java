package com.casamon.formacao.controllers;

import com.casamon.formacao.controllers.DTOs.FormadorDTO;
import com.casamon.formacao.controllers.forms.FormadorForm;
import com.casamon.formacao.models.Formador;
import com.casamon.formacao.repositories.FormadorRepository;
import com.casamon.formacao.services.FormadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/formadores")
public class FormadorController {

    @Autowired
    private FormadorRepository formadorRepository;

    @Autowired
    private FormadorService formadorService;


    @PostMapping("/criar")
    public ResponseEntity<?> criarFormador (@RequestBody FormadorForm formadorForm){
        if(!formadorRepository.existsByEmail(formadorForm.getEmail())){
            String senha = (new BCryptPasswordEncoder().encode(formadorForm.getSenha()));
            Formador f = new Formador(formadorForm.getNome(), formadorForm.getEmail(), formadorForm.getSenha());
            formadorRepository.save(f);
            return new ResponseEntity<>(new FormadorDTO(f), HttpStatus.CREATED);
        }else return new ResponseEntity<>("Usuário já existe", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listaFormadores(){
        List<Formador> listaF = formadorRepository.findAll();
        if(!listaF.isEmpty()) return new ResponseEntity<>(listaF.stream().map(FormadorDTO::new).collect(Collectors.toList()), HttpStatus.OK);
        else return new ResponseEntity<>("Nenhum formador encontrado", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/formandos")
    public ResponseEntity<?> listaFormandos(){
        Formador f= formadorService.retornaFormadorLogado();
        List<String> nomesFormandos = f.getFormandos().stream().map(formando -> formando.getNome()).collect(Collectors.toList());
        if (!nomesFormandos.isEmpty()) return ResponseEntity.ok().body(nomesFormandos);
        else return new ResponseEntity<>("Nenhum formador encontrado", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> retornaFormador(@PathVariable Long id){
        Formador f = formadorService.encontraFormador(id);
        return new ResponseEntity<>(new FormadorDTO(f), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> retornaFormadorPorEmail(@PathVariable String email){
        Formador f = formadorRepository.findByEmail(email).orElseThrow(() -> new NullPointerException("Não foi encontrado o usuário com email = " + email));
        return new ResponseEntity<>(new FormadorDTO(f), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizaFormador (@PathVariable Long id, @RequestBody FormadorForm formadorForm){
        if(!formadorRepository.existsById(id)) return new ResponseEntity<>("Não foi encontrado o usuário com id = " + id, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(new FormadorDTO(formadorForm.atualizar(id, formadorRepository)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletaFormador(@PathVariable Long id){
        Formador f = formadorService.encontraFormador(id);
        formadorRepository.delete(f);
        return new ResponseEntity<>("Deletado com sucesso", HttpStatus.OK);
    }

}
