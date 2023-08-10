package com.wallas.crudspring.service;

import com.wallas.crudspring.exception.DepartamentosDiferentesException;
import com.wallas.crudspring.model.Departamento;
import com.wallas.crudspring.model.Pessoa;
import com.wallas.crudspring.model.Tarefa;
import com.wallas.crudspring.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository repository;
    private final PessoaService pessoaService;
    private final DepartamentoService departamentoService;

    public Tarefa criar (Tarefa tarefa){

        String departamentoTitulo = tarefa.getDepartamentoTarefa().getTitulo();

        Departamento departamentoExistente = departamentoService.findByTitulo(departamentoTitulo);

        if (departamentoExistente == null) {
            Departamento departamentoSalvo = departamentoService.salvar(tarefa.getDepartamentoTarefa());
            tarefa.setDepartamentoTarefa(departamentoSalvo);
        } else {
            tarefa.setDepartamentoTarefa(departamentoExistente);
        }

        return repository.save(tarefa);
    }

    public Tarefa alocar(Long idPessoa, Long idTarefa){
        Pessoa pessoa = pessoaService.verificarPessoa(idPessoa);
        Tarefa tarefa = this.verificarTarefa(idTarefa);

        if (!pessoa.getDepartamento().equals(tarefa.getDepartamentoTarefa())) {
            throw new DepartamentosDiferentesException("A pessoa e a tarefa não pertencem ao mesmo departamento");
        }

        tarefa.setPessoaAlocada(pessoa);

        repository.save(tarefa);

        return tarefa;
    }

    public Tarefa finalizar(Long id){
        Tarefa tarefa = this.verificarTarefa(id);
        tarefa.setFinalizado(true);
        repository.save(tarefa);
        return tarefa;
    }

    public List<Tarefa> listarTarefasSemPessoaAlocada() {
        return repository.findTop3ByPessoaAlocadaIsNullOrderByPrazoAsc();
    }

    public Tarefa atualizar(Long id, Tarefa nova) {
        Tarefa tarefaExistente = verificarTarefa(id);

        if (nova.getTitulo() != null) {
            tarefaExistente.setTitulo(nova.getTitulo());
        }
        if (nova.getDescricao() != null) {
            tarefaExistente.setDescricao(nova.getDescricao());
        }
        if (nova.getPrazo() != null) {
            tarefaExistente.setPrazo(nova.getPrazo());
        }
        if (nova.getDepartamentoTarefa() != null) {
            tarefaExistente.setDepartamentoTarefa(nova.getDepartamentoTarefa());
        }
        if (nova.getDuracao() != null) {
            tarefaExistente.setDuracao(nova.getDuracao());
        }
        if (nova.isFinalizado() != tarefaExistente.isFinalizado()) {
            tarefaExistente.setFinalizado(nova.isFinalizado());
        }
        if (nova.getPessoaAlocada() != null && nova.getPessoaAlocada().getId() != null) {
            Pessoa pessoa = pessoaService.verificarPessoa(nova.getPessoaAlocada().getId());
            tarefaExistente.setPessoaAlocada(pessoa);
        }

        return repository.save(tarefaExistente);
    }

    public void deletar(Long id){
        Tarefa tarefa = verificarTarefa(id);
        repository.delete(tarefa);
    }

    private Tarefa verificarTarefa(Long id){
        return repository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("Tarefa não localizada - id: " + id));
    }
}
