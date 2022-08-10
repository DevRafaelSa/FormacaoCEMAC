package com.casamon.formacao.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Questao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String texto;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp dataCriacao;

    @ManyToOne
    @JoinColumn(name = "exercicio_id", nullable = false)
    private Exercicio exercicio;


    @OneToMany(mappedBy = "questao", cascade = CascadeType.ALL)
    private List<Resposta> respostas;

    public Questao(String texto, Exercicio exercicio){
        this.texto = texto;
        this.exercicio = exercicio;
        this.respostas = new ArrayList<>();
    }
}
