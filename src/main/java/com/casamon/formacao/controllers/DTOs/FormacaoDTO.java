package com.casamon.formacao.controllers.DTOs;

import com.casamon.formacao.models.Formacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class FormacaoDTO {

    private Long id;
    private int numero;
    private String linkMaterial;
    private Long exercicioId;
    private String formadorNome;


    public FormacaoDTO(Formacao f){
        this.id = f.getId();
        this.numero = f.getNumero();
        this.linkMaterial = f.getLinkMaterial();
        this.exercicioId = f.getExercicio().getId();
        this.formadorNome = f.getFormador().getNome();
    }
}
