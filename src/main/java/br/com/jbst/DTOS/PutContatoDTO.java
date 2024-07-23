package br.com.jbst.DTOS;


import java.util.UUID;

import lombok.Data;



@Data
public class PutContatoDTO {
	

	private UUID idContato;
	private UUID idEmpresa;
	private String contato;
	private String telefone_1;
	private String telefone_2;
	private String email;


}
