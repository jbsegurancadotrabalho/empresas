package br.com.jbst.DTOS;

import java.util.UUID;

import lombok.Data;

@Data
public class PutEmpresaUsuariosDTO {
	private String razaosocial;
	private String nomefantasia;
	private String cnpj;
	private String status;
	private String responsavel_sistema;
	private String telefone_responsavel;
    private UUID idEmpresa;
}
