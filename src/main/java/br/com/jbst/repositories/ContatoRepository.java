package br.com.jbst.repositories;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


import br.com.jbst.entities.Contato;


public interface ContatoRepository extends JpaRepository<Contato, UUID>{
	


}

