package br.com.jbst.services;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jbst.DTO.GetUsuariosDTO;
import br.com.jbst.entities.Usuario;
import br.com.jbst.repositories.IUsuarioRepository;

@Service
public class UsuariosService {

	@Autowired
	ModelMapper modelMapper;	
	
	@Autowired
	IUsuarioRepository usuarioRepository;
	
	
	 public GetUsuariosDTO getUsuarioById(UUID id) {
	        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
	        if (usuarioOpt.isPresent()) {
	            return modelMapper.map(usuarioOpt.get(), GetUsuariosDTO.class);
	        } else {
	            // Handle the case where the user is not found
	            // You can throw an exception or return null, depending on your design
	            throw new RuntimeException("Usuário não encontrado");
	        }
	    }
}
