package br.com.jbst.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jbst.DTO.GetUsuariosDTO;
import br.com.jbst.services.UsuariosService;

@RestController
@RequestMapping(value = "/api/usuarios")
public class UsuariosController {

	
	@Autowired
	UsuariosService usuarioService;
	
	@GetMapping("{id}")
	public ResponseEntity<GetUsuariosDTO> ConsultarUmaEmpresa(@PathVariable("id") UUID id)throws Exception {
			return ResponseEntity.status(HttpStatus.OK).body(usuarioService.getUsuarioById(id));
	}
}
