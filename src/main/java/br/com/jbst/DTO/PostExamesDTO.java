package br.com.jbst.DTO;

import java.time.Instant;
import java.util.UUID;

import lombok.Data;

@Data
public class PostExamesDTO {

	private UUID idFuncao;
	private Instant dataHoraCriacao;
	private String exame;
	private String tipo;
}
