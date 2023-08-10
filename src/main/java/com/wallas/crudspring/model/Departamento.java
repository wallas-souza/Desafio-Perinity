package com.wallas.crudspring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "departamento")
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @JsonIgnore
    @OneToMany(mappedBy = "departamento")
    private List<Pessoa> pessoas;

    @JsonIgnore
    @OneToMany(mappedBy = "departamentoTarefa")
    private List<Tarefa> tarefas;
}
