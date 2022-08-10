package com.casamon.formacao.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Formacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int numero;

    @Column(nullable = false)
    private String linkMaterial;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp dataCriacao;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "exercicio_id", referencedColumnName = "id")
    private Exercicio exercicio;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "formador_id", referencedColumnName = "id")
    private Formador formador;

    public Formacao(int numero, String linkMaterial, Formador f) {
        this.numero= numero;
        this.linkMaterial = linkMaterial;
        this.formador = f;
        this.exercicio = new Exercicio(this);
    }
}
