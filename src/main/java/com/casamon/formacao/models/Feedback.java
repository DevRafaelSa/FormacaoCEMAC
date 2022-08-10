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
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String textoFeedback;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp dataCriacao;

    @ManyToOne
    @JoinColumn(name = "formador_id", nullable = false)
    private Formador formador;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "resposta_id", referencedColumnName = "id")
    private Resposta resposta;

    public Feedback(String feedbacksQuestao, Formador f, Resposta r){
        this.textoFeedback = feedbacksQuestao;
        this.formador = f;
        this.resposta = r;
    }
}
