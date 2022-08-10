package com.casamon.formacao.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Exercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp dataCriacao;

    @OneToOne(mappedBy = "exercicio")
    private Formacao formacao;

    @OneToMany(mappedBy = "exercicio", cascade = CascadeType.ALL)
    private List<Questao> questoes;

    public Exercicio(Formacao f){
        this.formacao = f;
        this.questoes = new ArrayList<>();
    }


}
