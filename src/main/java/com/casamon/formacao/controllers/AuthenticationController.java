package com.casamon.formacao.controllers;

import com.casamon.formacao.controllers.forms.FormadorForm;
import com.casamon.formacao.controllers.forms.LoginForm;
import com.casamon.formacao.controllers.forms.MembroForm;
import com.casamon.formacao.models.Formador;
import com.casamon.formacao.models.Membro;
import com.casamon.formacao.repositories.FormadorRepository;
import com.casamon.formacao.repositories.MembroRepository;
import com.casamon.formacao.services.FormadorService;
import com.casamon.formacao.services.JwtUserDetailsService;
import com.casamon.formacao.util.JwtTokenUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    protected final Log logger = LogFactory.getLog(getClass());

    final MembroRepository membroRepository;
    final FormadorRepository formadorRepository;
    final AuthenticationManager authenticationManager;
    final JwtUserDetailsService userDetailsService;
    final JwtTokenUtil jwtTokenUtil;
    final FormadorService formadorService;

    public AuthenticationController(MembroRepository membroRepository, FormadorRepository formadorRepository, AuthenticationManager authenticationManager,
                                    JwtUserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil, FormadorService formadorService) {
        this.membroRepository = membroRepository;
        this.formadorRepository = formadorRepository;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.formadorService = formadorService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginForm loginForm){
    Map<String, Object> responseMap = new HashMap<>();
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginForm.getEmail(),
                            loginForm.getSenha()));
            if (auth.isAuthenticated()) {
                logger.info("Logged In");
                UserDetails userDetails = userDetailsService.loadUserByUsername(loginForm.getEmail());
                String token = jwtTokenUtil.generateToken(userDetails);
                responseMap.put("error", false);
                responseMap.put("message", "Logged In");
                responseMap.put("token", token);
                return ResponseEntity.ok(responseMap);
            } else {
                responseMap.put("error", true);
                responseMap.put("message", "Invalid Credentials");
                return ResponseEntity.status(401).body(responseMap);
            }
        } catch (DisabledException e) {
            e.printStackTrace();
            responseMap.put("error", true);
            responseMap.put("message", "User is disabled");
            return ResponseEntity.status(500).body(responseMap);
        } catch (BadCredentialsException e) {
            responseMap.put("error", true);
            responseMap.put("message", "Invalid Credentials");
            return ResponseEntity.status(401).body(responseMap);
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("error", true);
            responseMap.put("message", "Something went wrong");
            return ResponseEntity.status(500).body(responseMap);
        }
    }
    @PostMapping("/criarMembro")
    public ResponseEntity<?> criarMembro (@RequestBody MembroForm membroForm){
        if(!membroRepository.existsByEmail(membroForm.getEmail())){
            Formador f = formadorService.encontraFormador(membroForm.getIdFormador());
            String senha = (new BCryptPasswordEncoder().encode(membroForm.getSenha()));
            Membro m = new Membro(membroForm.getNome(), membroForm.getEmail(), senha, f);
            membroRepository.save(m);
            Map<String, Object> responseMap = new HashMap<>();
            UserDetails userDetails = userDetailsService.loadUserByUsername(m.getEmail());
            String token = jwtTokenUtil.generateToken(userDetails);
            responseMap.put("error", false);
            responseMap.put("username", m.getEmail());
            responseMap.put("message", "Criado com sucesso");
            responseMap.put("token", token);
            return ResponseEntity.ok(responseMap);
        }else return new ResponseEntity<>("Usuário já existe", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/criarFormador")
    public ResponseEntity<?> criarFormador (@RequestBody FormadorForm formadorForm){
        if(!formadorRepository.existsByEmail(formadorForm.getEmail())){
            String senha = (new BCryptPasswordEncoder().encode(formadorForm.getSenha()));
            Formador f = new Formador(formadorForm.getNome(), formadorForm.getEmail(), senha);
            formadorRepository.save(f);
            Map<String, Object> responseMap = new HashMap<>();
            UserDetails userDetails = userDetailsService.loadUserByUsername(f.getEmail());
            String token = jwtTokenUtil.generateToken(userDetails);
            responseMap.put("error", false);
            responseMap.put("username", f.getEmail());
            responseMap.put("message", "Criado com sucesso");
            responseMap.put("token", token);
            return ResponseEntity.ok(responseMap);
        }else return new ResponseEntity<>("Usuário já existe", HttpStatus.BAD_REQUEST);
    }

}
