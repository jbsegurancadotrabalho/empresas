package br.com.jbst.DTOS;



import lombok.Data;



@Data
public class PostEnderecoDTO {
	
	private String cep;
	private String logradouro;
	private String complemento;
	private String numero;
	private String bairro;
	private String localidade;
	private String uf;


}
