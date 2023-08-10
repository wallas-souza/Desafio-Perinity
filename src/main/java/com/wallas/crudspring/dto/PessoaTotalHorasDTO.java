package com.wallas.crudspring.dto;

import com.wallas.crudspring.model.Departamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PessoaTotalHorasDTO {

    private Long id;
    private String nome;
    private Departamento departamento;
    private Long totalHoras;

}
