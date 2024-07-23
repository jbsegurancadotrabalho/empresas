package br.com.jbst.repositories;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.jbst.entities.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, UUID>{
	
	@Query("SELECT COUNT(f) > 0 FROM Funcionario f WHERE UPPER(f.cpf) = UPPER(:cpf)")
	boolean existsByCpf(@Param("cpf") String cpf);


	@Query("SELECT COUNT(f) > 0 FROM Funcionario f WHERE UPPER(f.rg) = UPPER(:rg)")
	boolean existsByRg(@Param("rg") String rg);

	}


	

	

