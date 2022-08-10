package com.casamon.formacao.controllers.DTOs;

import com.casamon.formacao.models.Membro;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class MembroDTO {
    private String email;
    private String nome;
    private String senha;
    private String nomeFormador;

    public MembroDTO(Membro m){
        this.email = m.getEmail();
        this.nome = m.getNome();
        this.senha = m.getSenha();
        this.nomeFormador = m.getFormador().getNome();
    }
}
