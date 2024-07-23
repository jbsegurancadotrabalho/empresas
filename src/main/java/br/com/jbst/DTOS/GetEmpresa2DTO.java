package br.com.jbst.DTOS;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class GetEmpresa2DTO {
	private UUID idEmpresa;
	private List<GetFuncionario1DTO> funcionarios;
}
