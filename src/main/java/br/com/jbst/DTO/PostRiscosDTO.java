package br.com.jbst.DTO;

import java.util.UUID;

import lombok.Data;

@Data
public class PostRiscosDTO {
	private UUID idFuncao;
	private String riscos;
	private String grupo;
	private String cor;
	private String grau;
	private String danos;
	private String medidasdecontrole;
}
