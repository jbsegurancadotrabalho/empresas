package br.com.jbst.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.jbst.DTOS.GetEmpresa1DTO;
import br.com.jbst.DTOS.GetEmpresa2DTO;
import br.com.jbst.DTOS.GetEmpresa3DTO;
import br.com.jbst.DTOS.GetEmpresaDTO;
import br.com.jbst.DTOS.PostEmpresaDTO;
import br.com.jbst.DTOS.PostEmpresaUsuariosDTO;
import br.com.jbst.DTOS.PutEmpresaDTO;
import br.com.jbst.DTOS.PutEmpresaUsuariosDTO;
import br.com.jbst.services.EmpresaService;

@RestController
@RequestMapping(value = "/api/empresa")
public class EmpresaController {
	
	@Autowired
	EmpresaService empresaService;
	
	
	@PostMapping
	public ResponseEntity<GetEmpresaDTO> CriarEmpresa(@RequestBody PostEmpresaDTO dto) throws Exception {	
		return ResponseEntity.status(HttpStatus.CREATED).body(empresaService.criarEmpresa(dto));		
	}
	
	@PostMapping("/criar-empresa-cliente")
    public ResponseEntity<GetEmpresaDTO> criarEmpresaCliente(@RequestBody PostEmpresaUsuariosDTO dto) {
        try {
            GetEmpresaDTO empresaDTO = empresaService.criarEmpresaCliente(dto);
            return ResponseEntity.ok(empresaDTO);
        } catch (Exception e) {
            // Handle the exception (e.g., return an error response)
            return ResponseEntity.badRequest().body(null);
        }
    }
	
	@PutMapping
	public ResponseEntity<GetEmpresaDTO> EditarEmpresa(@RequestBody PutEmpresaDTO dto) throws Exception {	
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(empresaService.editarEmpresa(dto));		
	}
	
	 @PutMapping("/editar-empresa-cliente")
	    public ResponseEntity<GetEmpresaDTO> editarEmpresaUsuarios(@RequestBody PutEmpresaUsuariosDTO dto) {
	        try {
	            GetEmpresaDTO updatedEmpresa = empresaService.editarEmpresaUsuarios(dto);
	            return ResponseEntity.ok(updatedEmpresa);
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.badRequest().body(null);
	        } catch (Exception e) {
	            return ResponseEntity.status(500).body(null);
	        }
	    }

	@GetMapping
	public ResponseEntity<List<GetEmpresaDTO>> get() throws Exception {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(empresaService.getAll(toString()));	
		
	}
	
	@GetMapping("consultar-empresa-trazendo-funcionario-e-funcao")
	public ResponseEntity<List<GetEmpresa3DTO>> GetAllFunc() throws Exception {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(empresaService.getAllFunc(toString()));	
		
	}
	
	@GetMapping("gerar-relatorio")
	public ResponseEntity<byte[]> getRelatorio() throws Exception {
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("relatorio.pdf").build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(empresaService.getReport());
	}

	@GetMapping("{id}")
	public ResponseEntity<GetEmpresa3DTO> ConsultarUmaEmpresa(@PathVariable("id") UUID id)throws Exception {
			return ResponseEntity.status(HttpStatus.OK).body(empresaService.getById(id));
	}
	
	
	@GetMapping("consultar-funcionario-da-empresa/{id}")
	public ResponseEntity<GetEmpresa2DTO> ConsultarFuncionarioDaEmpresa(@PathVariable("id") UUID id)throws Exception {
			return ResponseEntity.status(HttpStatus.OK).body(empresaService.consultarFuncionarioDaEmpresaPorId(id));
	}
	

	@DeleteMapping("{id}")
	public ResponseEntity<GetEmpresaDTO> ExcluirEmpresa (@PathVariable("id") UUID id) throws Exception {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(empresaService.excluirEmpresa(id));

	}
	   @GetMapping("/usuario/{idUsuario}")
	    public ResponseEntity<List<GetEmpresaDTO>> buscarEmpresasPorUsuario(@PathVariable UUID idUsuario) {
	        try {
	            List<GetEmpresaDTO> empresas = empresaService.buscarEmpresasPorIdUsuario(idUsuario);
	            return ResponseEntity.ok(empresas);
	        } catch (Exception e) {
	            e.printStackTrace(); // Aqui você pode lidar com o erro de outra maneira, como lançar uma exceção personalizada ou logar a exceção
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }
	   
	   @GetMapping("/buscarEmpresaFuncionario")
	    public List<GetEmpresa1DTO> buscarEmpresaFuncionario() {
	        try {
	            return empresaService.buscarEmpresaFuncionario();
	        } catch (Exception e) {
	            // Adicione aqui o tratamento de exceções adequado para sua aplicação
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar empresas", e);
	        }
	    }

}
