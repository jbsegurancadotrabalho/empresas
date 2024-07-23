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

import br.com.jbst.DTOS.GetFuncaoDTO;
import br.com.jbst.DTOS.PostFuncaoDTO;
import br.com.jbst.DTOS.PutFuncaoDTO;
import br.com.jbst.services.FuncaoService;

@RestController
@RequestMapping(value = "/api/funcao")
public class FuncaoController {

	
	@Autowired
	FuncaoService funcaoService;
	
	@PostMapping
	public ResponseEntity<GetFuncaoDTO> CriarFuncao(@RequestBody PostFuncaoDTO dto) throws Exception {	
		return ResponseEntity.status(HttpStatus.CREATED).body(funcaoService.criarFuncao(dto));		
	}
	
	@PutMapping
	public ResponseEntity<GetFuncaoDTO> EditarFuncao(@RequestBody PutFuncaoDTO dto) throws Exception {	
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(funcaoService.editarFuncao(dto));
	}
	@GetMapping
	public ResponseEntity<List<GetFuncaoDTO>> get() throws Exception {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(funcaoService.getAll());	
		
	}

	@GetMapping("{id}")
	public ResponseEntity<GetFuncaoDTO> ConsultarUmaFuncao(@PathVariable("id") UUID id)throws Exception {
			return ResponseEntity.status(HttpStatus.OK).body(funcaoService.getById(id));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<GetFuncaoDTO> ExcluirFuncao (@PathVariable("id") UUID id) throws Exception {

			return ResponseEntity
					.status(HttpStatus.OK)
					.body(funcaoService.excluirFuncao(id));

	}

	
}
