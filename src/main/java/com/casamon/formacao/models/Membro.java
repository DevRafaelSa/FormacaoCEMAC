package com.casamon.formacao.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class Membro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Email é obrigatório")
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "Senha é obrigatório")
    private String senha;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp dataCriacao;

    @ManyToOne
    @JoinColumn(name = "formador_id", nullable = false)
    private Formador formador;

    @OneToMany(mappedBy = "membro", cascade = CascadeType.ALL)
    private List<Resposta> respostasDeExercicios;

    public Membro(String nome, String email, String senha, Formador formador){
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.formador = formador;
        this.respostasDeExercicios = new ArrayList<>();
        formador.insereFormando(this);
    }


}
