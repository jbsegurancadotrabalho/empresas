package br.com.jbst.controllers;

import java.util.List;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jbst.DTOS.GetContatoDTO;
import br.com.jbst.DTOS.PostContatoDTO;
import br.com.jbst.DTOS.PutContatoDTO;
import br.com.jbst.services.ContatoService;

@RestController
@RequestMapping(value = "/api/contato")
public class ContatoController {
	
	
	@Autowired
	ContatoService contatoService;
	
	
	@PostMapping
	public ResponseEntity<GetContatoDTO> criarContato(@RequestBody PostContatoDTO dto) throws Exception {	
		return ResponseEntity.status(HttpStatus.CREATED).body(contatoService.criarContato(dto));		
	}
	@PutMapping
	public ResponseEntity<GetContatoDTO> EditarContato(@RequestBody PutContatoDTO dto) throws Exception {	
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(contatoService.editarContato(dto));

		
	}

	@GetMapping
	public ResponseEntity<ResponseEntity<List<GetContatoDTO>>> ConsultarContatos() throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(contatoService.consultarContatos());
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ResponseEntity<GetContatoDTO>> ConsultarUmContato(@PathVariable("id") UUID id)throws Exception {
			return ResponseEntity.status(HttpStatus.OK).body(contatoService.getById());
	}


	@DeleteMapping("{id}")
	public ResponseEntity<GetContatoDTO> ExcluirContato (@PathVariable("id") UUID id) throws Exception {

			return ResponseEntity
					.status(HttpStatus.OK)
					.body(contatoService.excluirContato(id));

	}


}
