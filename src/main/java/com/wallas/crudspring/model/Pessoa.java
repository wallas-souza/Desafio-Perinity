package com.wallas.crudspring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "idDepartamento")
    private Departamento departamento;

    @JsonManagedReference
    @OneToMany(mappedBy = "pessoaAlocada", cascade = CascadeType.ALL)
    private List<Tarefa> tarefas;
}
