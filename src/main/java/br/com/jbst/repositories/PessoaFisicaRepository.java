package br.com.jbst.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.jbst.entities.Empresa;
import br.com.jbst.entities.PessoaFisica;


public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, UUID>{

	
	@Query("select pf from PessoaFisica pf where pf.id = :id ")
	PessoaFisica find(@Param("id") UUID id);
    Optional<PessoaFisica> findByCpf(String cpf);
    
    
    
    @Query("SELECT pf FROM Usuario u JOIN u.pessoafisica pf WHERE u.id = :userId")
    List<PessoaFisica> findByUsuario_Id1(@Param("userId") UUID userId);

    List<PessoaFisica> findByUsuario_Id(UUID idUsuario);

}
