package com.casamon.formacao.services;

import com.casamon.formacao.models.Membro;
import com.casamon.formacao.repositories.MembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MembroService {

    @Autowired
    private MembroRepository membroRepository;


    public Membro retornaMembroLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return membroRepository.findByEmail(email).get();
    }

}
