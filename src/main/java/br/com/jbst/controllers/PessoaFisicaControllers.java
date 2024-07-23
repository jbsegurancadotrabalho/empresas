package br.com.jbst.controllers;

import java.util.List;
import java.util.Map;
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

import br.com.jbst.DTOS.GetEmpresaDTO;
import br.com.jbst.DTOS.GetPessoaFisicaDTO;
import br.com.jbst.DTOS.PostPessoaFisicaDTO;
import br.com.jbst.DTOS.PutPessoaFisicaDTO;
import br.com.jbst.services.PessoaFisicaService;

@RestController
@RequestMapping(value = "/api/pessoa-fisica")
public class PessoaFisicaControllers {
	@Autowired
	PessoaFisicaService pessoaFisicaService ;
	
	@PostMapping
	public ResponseEntity<GetPessoaFisicaDTO> criarPessoaFisica(@RequestBody PostPessoaFisicaDTO dto ) throws Exception{
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaFisicaService.criarPessoaFisica(dto));		

	}
	
	@PutMapping // Adicione o mapeamento para receber o ID na URL
	public ResponseEntity<GetPessoaFisicaDTO> editarPessoaFisica(@RequestBody PutPessoaFisicaDTO dto) throws Exception {
	    return ResponseEntity
	            .status(HttpStatus.OK)
	            .body(pessoaFisicaService.editarPessoaFisica(dto)); // Passe o ID aqui
	}


	@GetMapping
	public ResponseEntity<List<GetPessoaFisicaDTO>>ConsultarPessoaFisica(){
		return  ResponseEntity
				.status(HttpStatus.OK)
				.body(pessoaFisicaService.consultarPessoaFisica());	
				
	}
	
	@GetMapping("{id}")
	public  ResponseEntity<GetPessoaFisicaDTO> consultarUmaPessoaFisica(@PathVariable("id") UUID id){
		return ResponseEntity.status(HttpStatus.OK).body(pessoaFisicaService.consultarUmaPessoaFisica(id));

	}
	
	@DeleteMapping("{id}")
	public  ResponseEntity<GetPessoaFisicaDTO>excluirPessoaFisica(@PathVariable("id") UUID id){
		 try {
			 pessoaFisicaService.excluirPessoaFisica(id);
			 return ResponseEntity.status(HttpStatus.OK).build();
		    } 
	 
		 catch (Exception e) {
	            throw new RuntimeException("Pessoa física não encontrada"); 
	        }
		    }
	

	  @PutMapping(value = "incluir-assinatura", consumes = {"multipart/form-data"})
	    public ResponseEntity<Object> incluirAssinatura(
	            @RequestParam("id") UUID id,
	            @RequestParam("assinatura") MultipartFile assinatura) {

	        try {
	            // Capturando o tipo do arquivo
	            String tipo = assinatura.getContentType();

	            // Verificando se o formato é válido (JPG, JPEG, PNG, PDF)
	            if (tipo != null && (tipo.startsWith("image/"))) {
	                // Atualizando a assinatura da pessoa física e retornando a resposta
	                GetPessoaFisicaDTO result = pessoaFisicaService.incluirAssinatura(id, assinatura.getBytes());
	                return ResponseEntity.status(HttpStatus.OK).body(result);
	            } else {
	                // Tipo de arquivo inválido
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
	                        Map.of("error", "Tipo de arquivo inválido", "details", "A assinatura deve ser uma imagem (JPG, JPEG, PNG) ou PDF")
	                );
	            }
	        } catch (Exception ex) {
	            // Tratamento de exceção
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
	                    Map.of("error", "Falha ao processar a assinatura", "details", ex.getMessage())
	            );
	        }
	    }
	
	
	   @GetMapping("/usuario/{idUsuario}")
	    public ResponseEntity<List<GetPessoaFisicaDTO>> buscarEmpresasPorUsuario(@PathVariable UUID idUsuario) {
	        try {
	            List<GetPessoaFisicaDTO> pessoaFisica = pessoaFisicaService.buscarPessoaFisicaPorIdUsuario(idUsuario);
	            return ResponseEntity.ok(pessoaFisica);
	        } catch (Exception e) {
	            e.printStackTrace(); // Aqui você pode lidar com o erro de outra maneira, como lançar uma exceção personalizada ou logar a exceção
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }
	

		}

