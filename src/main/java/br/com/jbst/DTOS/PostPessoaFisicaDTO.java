package br.com.jbst.DTOS;



import java.util.UUID;

import lombok.Data;


@Data
public class PostPessoaFisicaDTO {
		private String pessoafisica;
		private String rg;
		private String cpf;
		private String senha_sistema;
		private String telefone_1;
		private String telefone_2;	
		private String email;
	    private UUID id;

}
