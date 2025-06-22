package com.moura.BalneabilidadeAPI.repository;

import com.moura.BalneabilidadeAPI.model.Praia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PraiaRepository extends JpaRepository<Praia, Long> {
    List<Praia> findByStatus(String status);
    List<Praia> findByNomeContainingIgnoreCase(String nome);
}
