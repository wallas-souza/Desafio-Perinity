package com.wallas.crudspring.service;

import com.wallas.crudspring.dto.MediaHorasPorTarefaDTO;
import com.wallas.crudspring.dto.PessoaTotalHorasDTO;
import com.wallas.crudspring.model.Departamento;
import com.wallas.crudspring.model.Pessoa;
import com.wallas.crudspring.model.Tarefa;
import com.wallas.crudspring.repository.DepartamentoRepository;
import com.wallas.crudspring.repository.PessoaRepository;
import com.wallas.crudspring.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository repository;
    private final TarefaRepository tarefaRepository;
    private final DepartamentoRepository departamentoRepository;

    public Pessoa criar(Pessoa pessoa) {
        List<Tarefa> tarefas = pessoa.getTarefas();
        pessoa.setTarefas(new ArrayList<>());

        String departamentoTitulo = pessoa.getDepartamento().getTitulo();

        Departamento departamentoExistente = departamentoRepository.findByTituloIgnoreCase(departamentoTitulo);

        if (departamentoExistente == null) {
            Departamento departamentoSalvo = departamentoRepository.save(pessoa.getDepartamento());
            pessoa.setDepartamento(departamentoSalvo);
        } else {
            pessoa.setDepartamento(departamentoExistente);
        }

        for (Tarefa tarefa : tarefas) {
            tarefa.setDepartamentoTarefa(pessoa.getDepartamento());
        }

        Pessoa pessoaSalva = repository.save(pessoa);

        for (Tarefa tarefa : tarefas) {
            tarefa.setPessoaAlocada(pessoaSalva);
            tarefaRepository.save(tarefa);
        }

        pessoaSalva.setTarefas(tarefas);

        return pessoaSalva;
    }

    public Pessoa atualizar(Long id, Pessoa nova) {
        Pessoa pessoa = verificarPessoa(id);

        if (nova.getNome() != null) {
            pessoa.setNome(nova.getNome());
        }
        if (nova.getDepartamento() != null) {
            pessoa.setDepartamento(nova.getDepartamento());
        }

        return repository.save(pessoa);
    }

    public void deletar(Long id){
        Pessoa pessoa = verificarPessoa(id);
        repository.delete(pessoa);
    }

    public List<PessoaTotalHorasDTO> listarPessoasComTotalHoras() {
        List<Object[]> resultados = repository.listarPessoasComTotalHoras();
        List<PessoaTotalHorasDTO> pessoasTotalHorasDTO = new ArrayList<>();

        for (Object[] resultado : resultados) {
            Long id = (Long) resultado[0];
            String nome = (String) resultado[1];
            Departamento departamento = (Departamento) resultado[2];
            Long totalHoras = (Long) resultado[3];

            PessoaTotalHorasDTO pessoaTotalHorasDTO = new PessoaTotalHorasDTO(id, nome, departamento, totalHoras);
            pessoasTotalHorasDTO.add(pessoaTotalHorasDTO);
        }

        return pessoasTotalHorasDTO;
    }

    public List<MediaHorasPorTarefaDTO> calcularMediaHorasPorTarefa(String nome, LocalDate dataInicio, LocalDate dataFim) {
        List<Object[]> resultados = repository.calcularMediaHorasPorTarefa(nome, dataInicio, dataFim);

        List<MediaHorasPorTarefaDTO> dtos = new ArrayList<>();

        for (Object[] resultado : resultados) {
            String nomePessoa = (String) resultado[0];
            Double mediaHoras = (Double) resultado[1];

            dtos.add(new MediaHorasPorTarefaDTO(nomePessoa, mediaHoras));
        }

        return dtos;
    }

    public Pessoa verificarPessoa(Long id){
        return repository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("Pessoa não localizada - id: " + id));
    }

}
