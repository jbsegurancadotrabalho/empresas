package br.com.jbst.DTOS;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostContatoDTO {

	private UUID idEmpresa;
	private String contato;
	private String telefone_1;
	private String telefone_2;
	private String email;

}
