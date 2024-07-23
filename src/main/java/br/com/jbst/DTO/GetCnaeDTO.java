package br.com.jbst.DTO;

import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class GetCnaeDTO {
	private UUID idCnae;
	private UUID idEmpresa;
	private Instant dataHoraCriacao;
    private String cnae;
	private String descricao;
}
