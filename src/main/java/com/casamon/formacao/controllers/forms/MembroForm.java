package com.casamon.formacao.controllers.forms;

import com.casamon.formacao.models.Membro;
import com.casamon.formacao.repositories.MembroRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MembroForm {

    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String nome;
    @NotBlank
    private String senha;
    @NotBlank
    private Long idFormador;

    public Membro atualizar(Long id, MembroRepository membroRepository){
        Membro m = membroRepository.getReferenceById(id);
        m.setNome(this.nome);
        m.setEmail(this.email);
        m.setSenha(this.senha);
        return m;
    }
}
