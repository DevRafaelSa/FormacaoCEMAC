package com.casamon.formacao.controllers.DTOs;

import com.casamon.formacao.models.Formador;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class FormadorDTO {
    private Long id;
    private String email;
    private String nome;
    private String senha;

    public FormadorDTO(Formador f){
        this.id = f.getId();
        this.email = f.getEmail();
        this.nome = f.getNome();
        this.senha = f.getSenha();
    }
}
