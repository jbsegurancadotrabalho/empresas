package br.com.jbst.DTO;


import java.time.Instant;
import java.util.UUID;

import lombok.Data;


@Data
public class PutAdicionalDTO {

		private UUID idfuncao;
		private UUID idadicional;
		private Instant dataHoraCriacao;
		private String tipo;
		private String descricao;
	}

	
	

