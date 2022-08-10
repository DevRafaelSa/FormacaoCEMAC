package com.casamon.formacao.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Formador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Email é obrigatório")
    @Column(unique = true, nullable = false)
    private String email;

    @Column
    @NotNull(message = "Nome é obrigatório")
    private String nome;

    @Column
    @NotNull(message = "Senha é obrigatório")
    private String senha;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp dataCriacao;

    @OneToMany(mappedBy = "formador", cascade = CascadeType.ALL)
    private List<Membro> formandos;

    @OneToMany(mappedBy = "formador", cascade = CascadeType.ALL)
    private List<Formacao> formacoes;

    @OneToMany(mappedBy = "formador", cascade = CascadeType.ALL)
    private List<Feedback> feedbacks;

    public Formador(String nome, String email, String senha){
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.formandos = new ArrayList<>();
        this.formacoes = new ArrayList<>();
        this.feedbacks = new ArrayList<>();
    }

    public void insereFormando(Membro m){
        formandos.add(m);
    }

    public void insereFormacao(Formacao f){
        formacoes.add(f);
    }
}
