package com.casamon.formacao.controllers.forms;

import com.casamon.formacao.models.Formacao;
import com.casamon.formacao.repositories.FormacaoRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class FormacaoForm {

    private int numero;
    private String linkMaterial;

    public Formacao atualizar(Long id, FormacaoRepository formacaoRepo){
        Formacao f = formacaoRepo.getReferenceById(id);
        f.setNumero(this.numero);
        f.setLinkMaterial(this.linkMaterial);
        return f;
    }
}
