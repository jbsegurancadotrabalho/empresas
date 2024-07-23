package br.com.jbst.DTOS;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import lombok.Data;


@Data
public class GetEmpresaDTO {
	
	private UUID idEmpresa;
	private Instant dataHoraCriacao;
	private String razaosocial;
	private String nomefantasia;
	private String cnpj;
	private String status;
	private String responsavel_sistema;
	private String email_usuario;
	private String senha_sistema;
	private String telefone_responsavel;
	


}
