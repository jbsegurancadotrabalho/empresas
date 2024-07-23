package br.com.jbst.DTO;


import java.util.UUID;

import lombok.Data;



@Data
public class PostAdicionalDTO {
	private UUID idFuncao;
	private String tipo;
	private String descricao;
}
