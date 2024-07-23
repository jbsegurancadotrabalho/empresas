package br.com.jbst.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.jbst.entities.Funcao;

public interface FuncaoRepository extends JpaRepository<Funcao, UUID>{
	
	@Query("select f from Funcao f where f.id = :id ")
	Funcao find(@Param("id") UUID id);

    // Verifica se já existe uma função com o mesmo nome
    @Query("SELECT COUNT(f) > 0 FROM Funcao f WHERE f.funcao = :funcao")
    boolean existsByFuncao(@Param("funcao") String funcao);
}