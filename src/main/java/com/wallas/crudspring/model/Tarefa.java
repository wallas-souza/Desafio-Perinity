package com.wallas.crudspring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tarefa")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descricao;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate prazo;

    @JsonIgnoreProperties("tarefas")
    @ManyToOne
    @JoinColumn(name = "idDepartamento")
    private Departamento departamentoTarefa;

    private Integer duracao;

    private boolean finalizado;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoaAlocada;
}
