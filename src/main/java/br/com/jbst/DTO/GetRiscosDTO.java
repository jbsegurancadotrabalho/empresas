package br.com.jbst.DTO;

import java.time.Instant;
import java.util.UUID;

import lombok.Data;

@Data
public class GetRiscosDTO {
	
	
	private UUID idFuncao;
	private UUID idRisco;
	private Instant dataHoraCriacao;
	private String riscos;
	private String grupo;
	private String cor;
	private String grau;
	private String danos;
	private String medidasdecontrole;
}
