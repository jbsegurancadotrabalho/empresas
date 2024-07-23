package br.com.jbst.DTOS;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostFuncionarioDTO {

	private UUID idEmpresa;
	private UUID idFuncao;
	private String nome;
	private String cpf;
	private String rg;
	private String status;	
}
