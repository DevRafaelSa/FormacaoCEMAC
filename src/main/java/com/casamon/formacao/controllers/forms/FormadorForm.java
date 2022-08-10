package com.casamon.formacao.controllers.forms;

import com.casamon.formacao.models.Formador;
import com.casamon.formacao.repositories.FormadorRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class FormadorForm {

    @NotBlank
    private String nome;

    @NotBlank @Email
    private String email;

    @NotBlank
    private String senha;

    public Formador atualizar(Long id, FormadorRepository formadorRepository){
        Formador f = formadorRepository.getReferenceById(id);
        f.setNome(this.nome);
        f.setEmail(this.email);
        f.setSenha(this.senha);
        return f;
    }
}
