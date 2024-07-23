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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.jbst.DTOS.GetFuncionario1DTO;
import br.com.jbst.DTOS.GetFuncionarioDTO;
import br.com.jbst.DTOS.PostFuncionarioDTO;
import br.com.jbst.DTOS.PutFuncionarioDTO;
import br.com.jbst.services.FuncionarioService;

@RestController
@RequestMapping(value = "/api/funcionario")
public class FuncionarioController {

	@Autowired
	FuncionarioService funcionarioService;

	@PostMapping
	public ResponseEntity<GetFuncionarioDTO> CriarFuncao(@RequestBody PostFuncionarioDTO dtos) throws Exception {
		return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioService.criarFuncionario(dtos));
	}

	@PutMapping
	public ResponseEntity<GetFuncionarioDTO> EditarFuncionario(@RequestBody PutFuncionarioDTO dto) throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.editarFuncionario(dto));

	}
	


	@PutMapping(value = "incluir-assinatura", consumes = { "multipart/form-data" })
	public ResponseEntity<GetFuncionarioDTO> IncluirAssinatura(@RequestParam("id") UUID id,
			@RequestParam("assinatura") MultipartFile assinatura) throws Exception {

		// capturando o tipo do arquivo
		String tipo = assinatura.getContentType();

		// verificando se o formato é válido (JPG, JPEG, PNG)
		if (tipo.equals("image/jpg") || tipo.equals("image/jpeg") || tipo.equals("image/png")) {
			// atualizando a foto do produto e retornando a resposta
			GetFuncionarioDTO result = funcionarioService.incluirAssinatura(id, assinatura.getBytes());
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	@GetMapping
	public ResponseEntity<ResponseEntity<List<GetFuncionarioDTO>>> ConsultarFuncionarios() throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.consultarFuncionarios());
	}

	
	  @GetMapping("/buscar-funcionarios-empresas")
	    public List<GetFuncionario1DTO> buscarFuncionarioEmpresas() throws Exception {
	        return funcionarioService.buscarFuncionarioEmpresas();
	    }
	  
	  @GetMapping("/buscar-funcionarios-empresa-por-id/{id}")
	    public ResponseEntity<GetFuncionario1DTO> buscarFuncionarioEmpresasPorId(@PathVariable UUID id) {
	        try {
	        	GetFuncionario1DTO documentosFree = funcionarioService.buscarFuncionarioEmpresasPorID(id);
	            return ResponseEntity.ok(documentosFree);
	        } catch (Exception e) {
	            return ResponseEntity.notFound().build();
	        }
	    }
	@GetMapping("{idFuncionario}")
	public ResponseEntity<GetFuncionarioDTO> ConsultarUmFuncionario(@PathVariable("idFuncionario") UUID idFuncionario)
			throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.getById(idFuncionario));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<GetFuncionarioDTO> ExcluirFuncionario(@PathVariable("id") UUID id) throws Exception {

		return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.excluirFuncionario(id));

	}
	
	@GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<GetFuncionarioDTO>> buscarFuncionariosPorIdUsuario(@PathVariable("idUsuario") UUID idUsuario) {
        List<GetFuncionarioDTO> funcionarios = funcionarioService.buscarFuncionariosPorIdUsuario(idUsuario);
        return ResponseEntity.ok(funcionarios);
    }

}
