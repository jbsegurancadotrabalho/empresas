package br.com.jbst.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jbst.entities.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, UUID> {

    @Query("SELECT e FROM Empresa e WHERE e.id = :id")
    Empresa find(@Param("id") UUID id);

    // Verifica se já existe uma empresa com o mesmo CNPJ
    @Query("SELECT COUNT(e) > 0 FROM Empresa e WHERE e.cnpj = :cnpj")
    boolean existsByCnpj(@Param("cnpj") String cnpj);

    @Query("SELECT COUNT(e) > 0 FROM Empresa e WHERE e.razaosocial = :razaoSocial")
    boolean existsByRazaoSocial(@Param("razaoSocial") String razaoSocial);
  
    @Query("SELECT COUNT(e) > 0 FROM Empresa e WHERE e.nomefantasia = :nomeFantasia")
    boolean existsByNomeFantasia(@Param("nomeFantasia") String nomeFantasia);

    
    List<Empresa> findByUsuario_Id(UUID idUsuario);

    
}
