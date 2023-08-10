package com.wallas.crudspring.controller;

import com.wallas.crudspring.dto.DepartamentoQuantidadeDTO;
import com.wallas.crudspring.model.Departamento;
import com.wallas.crudspring.service.DepartamentoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/departamentos")
public class DepartamentoController {

    private final DepartamentoService service;

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody @Validated Departamento departamento) {
        return new ResponseEntity<>(this.service.criar(departamento), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<DepartamentoQuantidadeDTO>> obterResumoDepartamentos() {
        List<DepartamentoQuantidadeDTO> resumoDepartamentos = service.obterResumoDepartamentos();
        return ResponseEntity.ok(resumoDepartamentos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        this.service.deletar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
