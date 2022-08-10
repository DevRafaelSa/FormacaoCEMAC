package com.casamon.formacao.services;

import com.casamon.formacao.models.Formador;
import com.casamon.formacao.models.Membro;
import com.casamon.formacao.repositories.FormadorRepository;
import com.casamon.formacao.repositories.MembroRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    final MembroRepository userRepository;
    final FormadorRepository formadorRepository;

    public JwtUserDetailsService(MembroRepository userRepository, FormadorRepository formadorRepository) {
        this.userRepository = userRepository;
        this.formadorRepository = formadorRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(userRepository.existsByEmail(username)){
            Optional<Membro> user = userRepository.findByEmail(username);
            List<GrantedAuthority> authorityList = new ArrayList<>();
            //authorityList.add(new SimpleGrantedAuthority("USER_ROLE"));
            return new User(user.get().getEmail(), user.get().getSenha(), new ArrayList<>());
        }else if(formadorRepository.existsByEmail(username)){
            Optional<Formador> user = formadorRepository.findByEmail(username);
            List<GrantedAuthority> authorityList = new ArrayList<>();
           // authorityList.add(new SimpleGrantesdAuthority("USER_ROLE"));
            return new User(user.get().getEmail(), user.get().getSenha(), new ArrayList<>());
        }else throw new UsernameNotFoundException(username);

    }

}
