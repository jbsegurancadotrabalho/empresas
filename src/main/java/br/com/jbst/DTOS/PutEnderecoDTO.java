package br.com.jbst.DTOS;


import java.util.UUID;

import lombok.Data;

@Data
public class PutEnderecoDTO {
	
	private UUID idEndereco;
	private String cep;
	private String logradouro;
	private String complemento;
	private String numero;
	private String bairro;
	private String localidade;
	private String uf;

}
