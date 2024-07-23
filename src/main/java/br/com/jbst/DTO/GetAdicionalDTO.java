package br.com.jbst.DTO;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAdicionalDTO {

		private UUID idadicional;
		private UUID idFuncao;
		private Instant dataHoraCriacao;
		private String tipo;
		private String descricao;
	}


