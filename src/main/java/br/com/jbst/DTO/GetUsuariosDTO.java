package br.com.jbst.DTO;

import java.util.UUID;

import lombok.Data;

@Data
public class GetUsuariosDTO {
	private UUID id;
	private String nome;
	private String email;

}
