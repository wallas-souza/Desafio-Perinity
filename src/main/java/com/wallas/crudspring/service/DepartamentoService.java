package com.wallas.crudspring.service;

import com.wallas.crudspring.dto.DepartamentoQuantidadeDTO;
import com.wallas.crudspring.exception.DepartamentoDuplicadoException;
import com.wallas.crudspring.model.Departamento;
import com.wallas.crudspring.repository.DepartamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DepartamentoService {

    private final DepartamentoRepository repository;
    public Departamento criar(Departamento departamento) {
        String titulo = departamento.getTitulo();
        Departamento departamentoExistente = repository.findByTituloIgnoreCase(titulo);

        if (departamentoExistente != null) {
            throw new DepartamentoDuplicadoException("Já existe um departamento com o mesmo título");
        }

        return repository.save(departamento);
    }

    public void deletar(Long id){
        Departamento departamento = verificarDepartamento(id);
        repository.delete(departamento);
    }

    public List<DepartamentoQuantidadeDTO> obterResumoDepartamentos() {
        return repository.countPeopleAndTasksPerDepartment();
    }

    public Departamento verificarDepartamento(Long id){
        return repository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("Departamento não localizado - id: " + id));
    }

    public Departamento salvar(Departamento departamento){
        return repository.save(departamento);
    }

    public Departamento findByTitulo(String titulo){
        return repository.findByTituloIgnoreCase(titulo);
    }
}
