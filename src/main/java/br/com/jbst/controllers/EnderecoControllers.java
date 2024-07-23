package br.com.jbst.controllers;

import java.util.List;
import java.util.UUID;

import javax.security.auth.login.AccountNotFoundException;

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

import br.com.jbst.DTOS.GetEnderecoDTO;
import br.com.jbst.DTOS.PostEnderecoDTO;
import br.com.jbst.DTOS.PutEnderecoDTO;
import br.com.jbst.services.EnderecoService;



@RestController
@RequestMapping (value = "/api/endereco")
public class EnderecoControllers {
	
	@Autowired
	EnderecoService enderecoService;
	
	@PostMapping
	public ResponseEntity<GetEnderecoDTO> criarEndereco(@RequestBody PostEnderecoDTO dto){
		return ResponseEntity.status(HttpStatus.CREATED).body(enderecoService.criarEndereco(dto));		

	}
	
	@PutMapping
	public ResponseEntity<GetEnderecoDTO> editarEndereco(@RequestBody PutEnderecoDTO dto){
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(enderecoService.editarEndereco(dto));
	}

	@GetMapping
	public  ResponseEntity<List<GetEnderecoDTO>> consultarEnderecos(){
		return  ResponseEntity
				.status(HttpStatus.OK)
				.body(enderecoService.consultarEnderecos());

	}
	
	@GetMapping("{id}")
	public  ResponseEntity<GetEnderecoDTO>  consultarUmEndereco(@PathVariable("id") UUID id) throws AccountNotFoundException{
		return ResponseEntity.status(HttpStatus.OK).body(enderecoService.consultarUmEndereco(id));

	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirEndereco(@PathVariable("id") UUID id) {
	    try {
	        enderecoService.excluirEndereco(id);
	        return ResponseEntity.status(HttpStatus.OK).build();
	    } catch (AccountNotFoundException e) {
	        // Se o endereço não for encontrado, você pode retornar uma resposta 404 Not Found
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }
	}

}
