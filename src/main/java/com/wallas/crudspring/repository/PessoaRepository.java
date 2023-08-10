package com.wallas.crudspring.repository;

import com.wallas.crudspring.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa,Long> {
    @Query("SELECT p.id, p.nome, d, SUM(t.duracao) " +
            "FROM Pessoa p " +
            "LEFT JOIN p.tarefas t " +
            "LEFT JOIN p.departamento d " +
            "GROUP BY p.id, p.nome, d")
    List<Object[]> listarPessoasComTotalHoras();

    @Query("SELECT p.nome, " +
            "AVG(t.duracao) " +
            "FROM Pessoa p " +
            "JOIN p.tarefas t " +
            "WHERE LOWER(p.nome) = LOWER(:nome) " +
            "AND t.prazo >= :dataInicio AND t.prazo <= :dataFim " +
            "GROUP BY p.nome")
    List<Object[]> calcularMediaHorasPorTarefa(@Param("nome") String nome,
                                               @Param("dataInicio") LocalDate dataInicio,
                                               @Param("dataFim") LocalDate dataFim);


}

