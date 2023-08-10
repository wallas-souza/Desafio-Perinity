package com.wallas.crudspring.repository;

import com.wallas.crudspring.dto.DepartamentoDTO;
import com.wallas.crudspring.model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento,Long> {
    Departamento findByTituloIgnoreCase(String titulo);

    @Query("SELECT new com.wallas.crudspring.dto.DepartamentoDTO(d.id, d.titulo, " +
            "(SELECT COUNT(p) FROM Pessoa p WHERE p.departamento = d), " +
            "(SELECT COUNT(t) FROM Tarefa t WHERE t.departamentoTarefa = d)) " +
            "FROM Departamento d")
    List<DepartamentoDTO> countPeopleAndTasksPerDepartment();
}

