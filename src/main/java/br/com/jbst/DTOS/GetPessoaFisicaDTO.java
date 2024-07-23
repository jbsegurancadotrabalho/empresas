package br.com.jbst.DTOS;

import java.time.Instant;

import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.jbst.config.InstantSerializer;
import lombok.Data;


@Data
public class GetPessoaFisicaDTO {		
    private UUID idpessoafisica;
	@JsonSerialize(using = InstantSerializer.class)
	private Instant dataHoraCriacao;
	private String pessoafisica;
	private String rg;
	private String cpf;
	private String telefone_1;
	private String telefone_2;	
	private String email;
	private String senha_sistema;
	private byte[] assinatura_pessoafisica;

}
