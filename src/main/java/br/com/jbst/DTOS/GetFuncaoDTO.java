package br.com.jbst.DTOS;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFuncaoDTO {
	
	private UUID idFuncao;
    private Instant dataHoraCriacao;
	private String funcao;
	private String cbo;
    private String descricao;
    private String gro;
    private String gp;

    public static UUID getFuncaoId() {
    	// TODO Auto-generated method stub
    	return null;
    	}

}
