package com.casamon.formacao.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String textoReposta;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp dataCriacao;

    @ManyToOne
    @JoinColumn(name = "membro_id", nullable = false)
    private Membro membro;

    @ManyToOne
    @JoinColumn(name = "questao_id", nullable = false)
    private Questao questao;

    @OneToOne(mappedBy = "resposta")
    private Feedback feedback;

    public Resposta(String textoReposta, Membro membro, Questao questao){
        this.textoReposta = textoReposta;
        this.membro = membro;
        this.questao = questao;
        this.feedback = null;
    }
}
