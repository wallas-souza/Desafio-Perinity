package com.wallas.crudspring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MediaHorasPorTarefaDTO {

    private String nome;
    private Double mediaHoras;

    public MediaHorasPorTarefaDTO(String nome, Double mediaHoras) {
        this.nome = nome;
        this.mediaHoras = mediaHoras;
    }

}
