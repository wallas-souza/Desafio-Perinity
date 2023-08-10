package com.wallas.crudspring.controller;

import com.wallas.crudspring.model.Tarefa;
import com.wallas.crudspring.service.TarefaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    private final TarefaService service;

    @GetMapping("/pendentes")
    public ResponseEntity<?> listar() {
        return new ResponseEntity<>(this.service.listarTarefasSemPessoaAlocada(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody @Validated Tarefa tarefa) {
        return new ResponseEntity<>(this.service.criar(tarefa), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody @Validated Tarefa tarefa) {
        return new ResponseEntity<>(service.atualizar(id, tarefa), HttpStatus.OK);
    }

    @PutMapping("/alocar/{idPessoa}/{idTarefa}")
    public ResponseEntity<?> alocar(@PathVariable Long idPessoa,
                                    @PathVariable Long idTarefa) {
        return new ResponseEntity<>(service.alocar(idPessoa, idTarefa), HttpStatus.OK);
    }

    @PutMapping("/finalizar/{id}")
    public ResponseEntity<?> alocar(@PathVariable Long id) {
        return new ResponseEntity<>(service.finalizar(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        this.service.deletar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
