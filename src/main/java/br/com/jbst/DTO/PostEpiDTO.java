package br.com.jbst.DTO;

import java.time.Instant;
import java.util.UUID;

import lombok.Data;

@Data
public class PostEpiDTO {

	private UUID idFuncao;
	private Instant dataHoraCriacao;
	private String epi;
	private String ca;
}
