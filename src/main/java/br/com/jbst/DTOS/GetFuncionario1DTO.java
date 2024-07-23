package br.com.jbst.DTOS;

import java.time.Instant;
import java.util.UUID;

import lombok.Data;

@Data
public class GetFuncionario1DTO {
	private UUID idFuncionario;
	private Instant dataHoraCriacao;
	private String nome;
	private String cpf;
	private String rg;
	private String status;
	private String matricula;
}
