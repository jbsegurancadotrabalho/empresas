package br.com.jbst.DTOS;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFuncionarioDTO {

	private UUID idEmpresa;
	private UUID idFuncao;
	private UUID idFuncionario;
	private Instant dataHoraCriacao;
	private String nome;
	private String cpf;
	private String rg;
	private String status;
	private String matricula;
	private byte[] assinatura;
	private GetFuncaoDTO funcao;
    private GetEmpresa1DTO empresa;

	public static UUID getEmpresaId() {
		// TODO Auto-generated method stub
		return null;
		}
	
	public static UUID getFuncaoId() {
		// TODO Auto-generated method stub
		return null;
		}

}
