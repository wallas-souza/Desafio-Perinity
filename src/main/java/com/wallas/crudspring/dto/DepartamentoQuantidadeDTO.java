package com.wallas.crudspring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartamentoQuantidadeDTO {

    private Long id;
    private String titulo;
    private Long quantidadePessoas;
    private Long quantidadeTarefas;

    public DepartamentoQuantidadeDTO(Long id, String titulo, Long quantidadePessoas, Long quantidadeTarefas) {
        this.id = id;
        this.titulo = titulo;
        this.quantidadePessoas = quantidadePessoas;
        this.quantidadeTarefas = quantidadeTarefas;
    }
}
