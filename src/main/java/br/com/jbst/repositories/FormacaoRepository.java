package br.com.jbst.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.jbst.entities.Formacao;

public interface FormacaoRepository extends JpaRepository <Formacao, UUID[]>{
	 @Query("SELECT f FROM Formacao f WHERE f.idFormacao = :idFormacao")
	    Optional<Formacao> findById(@Param("idFormacao") UUID idFormacao);
	 


	 List<Formacao> findByPessoafisica_Idpessoafisica(UUID idPessoaFisica);
}
