package com.wallas.crudspring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PessoaMediaHorasDTO {

    private Long id;
    private String nome;
    private Double mediaHoras;
}
