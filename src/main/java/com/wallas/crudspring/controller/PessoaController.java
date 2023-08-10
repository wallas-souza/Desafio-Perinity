package com.wallas.crudspring.controller;

import com.wallas.crudspring.dto.MediaHorasPorTarefaDTO;
import com.wallas.crudspring.dto.ParametrosEntradaDTO;
import com.wallas.crudspring.model.Pessoa;
import com.wallas.crudspring.service.PessoaService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    private final PessoaService service;

    @GetMapping()
    public ResponseEntity<?> buscaPessoas()  {
        return ResponseEntity.ok(service.listarPessoasComTotalHoras());
    }

    @GetMapping("/gastos")
    public ResponseEntity<List<MediaHorasPorTarefaDTO>> calcularMediaHorasPorTarefa(@RequestBody ParametrosEntradaDTO parametros) {
        LocalDate dataInicio = LocalDate.parse(parametros.getDataInicio(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate dataFim = LocalDate.parse(parametros.getDataFim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        List<MediaHorasPorTarefaDTO> resultados = service.calcularMediaHorasPorTarefa(parametros.getNome(), dataInicio, dataFim);
        return ResponseEntity.ok(resultados);
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody @Validated Pessoa pessoa) {
        return new ResponseEntity<>(this.service.criar(pessoa), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                              @RequestBody @Validated Pessoa pessoa) {
        return new ResponseEntity<>(service.atualizar(id, pessoa), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        this.service.deletar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
