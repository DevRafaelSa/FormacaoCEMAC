package com.casamon.formacao.services;

import com.casamon.formacao.models.Formador;
import com.casamon.formacao.repositories.FormadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class FormadorService {

    @Autowired
    private FormadorRepository formadorRepository;

    public Formador encontraFormador(Long id){
        Formador f = formadorRepository.findById(id).orElseThrow(() -> new NullPointerException("Não foi encontrado o usuário com id = " + id));
        return f;
    }

    public Formador retornaFormadorLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return formadorRepository.findByEmail(email).get();
    }
}
