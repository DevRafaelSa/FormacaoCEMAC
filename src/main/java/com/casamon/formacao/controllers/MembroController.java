package com.casamon.formacao.controllers;

import com.casamon.formacao.controllers.DTOs.MembroDTO;
import com.casamon.formacao.controllers.forms.MembroForm;
import com.casamon.formacao.models.Formador;
import com.casamon.formacao.models.Membro;
import com.casamon.formacao.repositories.FormadorRepository;
import com.casamon.formacao.repositories.MembroRepository;
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
@RequestMapping("/membros")
public class MembroController {

    @Autowired
    private MembroRepository membroRepository;

    @Autowired
    private FormadorRepository formadorRepository;

    @Autowired
    private FormadorService formadorService;

    @PostMapping("/criar")
    public ResponseEntity<?> criarMembro (@RequestBody MembroForm membroForm){
        if(!membroRepository.existsByEmail(membroForm.getEmail())){
            Formador f = formadorService.encontraFormador(membroForm.getIdFormador());
            String senha = (new BCryptPasswordEncoder().encode(membroForm.getSenha()));
            Membro m = new Membro(membroForm.getNome(), membroForm.getEmail(), senha, f);
            membroRepository.save(m);
            return new ResponseEntity<>(new MembroDTO(m), HttpStatus.CREATED);
        }else return new ResponseEntity<>("Usuário já existe", HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<?> listaMembros(){
        List<Membro> listaM = membroRepository.findAll();
        if(!listaM.isEmpty())
            return new ResponseEntity<>(listaM.stream().map(MembroDTO::new).collect(Collectors.toList()), HttpStatus.OK);
        else return new ResponseEntity<>("Nenhum membro encontrado", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> retornaMembro(@PathVariable Long id){
        Membro m = membroRepository.findById(id).orElseThrow(() -> new NullPointerException("Não foi encontrado o usuário com id = " + id));
        return new ResponseEntity<>(new MembroDTO(m), HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> retornaMembroPorEmail(@PathVariable String email){
        Membro m = membroRepository.findByEmail(email).orElseThrow(() -> new NullPointerException("Não foi encontrado o usuário com email = " + email));
        return new ResponseEntity<>(new MembroDTO(m), HttpStatus.OK);
    }


    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizaMembro (@PathVariable Long id, @RequestBody MembroForm membroForm){
        if(!membroRepository.existsById(id)) return new ResponseEntity<>("Não foi encontrado o usuário com id = " + id, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(new MembroDTO(membroForm.atualizar(id, membroRepository)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletaMembro(@PathVariable Long id){
        Membro m = membroRepository.findById(id).orElseThrow(() -> new NullPointerException("Não foi encontrado o usuário com id = " + id));
        membroRepository.delete(m);
        return new ResponseEntity<>("Deletado com sucesso", HttpStatus.OK);
    }
}
