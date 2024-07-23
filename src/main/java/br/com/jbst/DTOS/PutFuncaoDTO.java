package br.com.jbst.DTOS;


import java.util.UUID;
import lombok.Data;

@Data
public class PutFuncaoDTO {
	
   private UUID idFuncao;
   private String funcao;
   private String cbo;
   private String descricao;
	private String gro;
	private String gp;
	
}
