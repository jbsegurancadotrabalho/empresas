package br.com.jbst.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import br.com.jbst.DTOS.GetContatoDTO;
import br.com.jbst.DTOS.PostContatoDTO;
import br.com.jbst.DTOS.PutContatoDTO;
import br.com.jbst.entities.Contato;
import br.com.jbst.repositories.ContatoRepository;
import br.com.jbst.repositories.EmpresaRepository;

@Service
public class ContatoService {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	ContatoRepository contatoRepository;

	@Autowired
	EmpresaRepository empresaRepository;

	public GetContatoDTO criarContato(PostContatoDTO dto) throws Exception {

		try {

			Contato contato = modelMapper.map(dto, Contato.class);
			contato.setIdContato(UUID.randomUUID());
			contato.setDataHoraCriacao(Instant.now());
			contato.setEmpresa(empresaRepository.findById(dto.getIdEmpresa()).get());
			contatoRepository.save(contato);
			return modelMapper.map(contatoRepository.findById(contato.getIdContato()), GetContatoDTO.class);

		}

		catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public GetContatoDTO editarContato(PutContatoDTO dto) throws Exception {

		// verificando se o produto existe (baseado no ID informado)
		Optional<Contato> contatos = contatoRepository.findById(dto.getIdContato());
		if (contatos.isEmpty())
			throw new IllegalArgumentException("Chamado inválido: " + dto.getIdContato());

		// capturando o produto do banco de dados
		Contato contato = contatos.get();
		contato = modelMapper.map(dto, Contato.class);
		contato.setDataHoraCriacao(Instant.now());
		contato.setEmpresa(empresaRepository.findById(dto.getIdEmpresa()).get());
		contatoRepository.save(contato);

		// copiar os dados do produto para o DTO de resposta
		// e retornar os dados (ProdutosGetDTO)
		return modelMapper.map(contatoRepository.findAll(), GetContatoDTO.class);
	}

	public ResponseEntity<List<GetContatoDTO>> consultarContatos() {

		try {
			List<Contato> contatos = contatoRepository.findAll();
			List<GetContatoDTO> lista = new ArrayList<GetContatoDTO>();

			for (Contato contato : contatos) {
				GetContatoDTO dto = new GetContatoDTO();

				dto.setIdContato(contato.getIdContato());
				dto.setDataHoraCriacao(contato.getDataHoraCriacao());
				dto.setContato(contato.getContato());
				dto.setTelefone_1(contato.getTelefone_1());
				dto.setTelefone_2(contato.getTelefone_2());
				dto.setEmail(contato.getEmail());
				dto.setIdEmpresa(contato.getEmpresa().getIdEmpresa());

				lista.add(dto);
			}

			return ResponseEntity.status(200).body(lista);
		} catch (Exception e) {
			// HTTP 500 (INTERNAL SERVER ERROR)
			return ResponseEntity.status(500).body(null);
		}
	}

	public ResponseEntity<GetContatoDTO> getById() {
		try {
			// buscar o produto baseado no ID
			Optional<Contato> optional = Optional.empty();
			// verificar se o produto foi encontrado
			if (optional.isPresent()) {
				// capturar os dados do produto
				Contato contato = optional.get();
				GetContatoDTO dto = new GetContatoDTO();
				dto.setIdContato(contato.getIdContato());
				dto.setDataHoraCriacao(contato.getDataHoraCriacao());
				dto.setContato(contato.getContato());
				dto.setTelefone_1(contato.getTelefone_1());
				dto.setTelefone_2(contato.getTelefone_2());
				dto.setEmail(contato.getEmail());
				dto.setIdEmpresa(contato.getEmpresa().getIdEmpresa());

				// HTTP 200 - OK
				return ResponseEntity.status(200).body(dto);
			} else {
				return ResponseEntity.status(204).body(null);
			}
		} catch (Exception e) {
			// HTTP 500 (INTERNAL SERVER ERROR)
			return ResponseEntity.status(500).body(null);
		}
	}

	public GetContatoDTO excluirContato(UUID id) throws Exception {

		Optional<Contato> contatos = contatoRepository.findById(id);
		if (contatos.isEmpty())
			throw new IllegalArgumentException("Chamado inválido: " + id);

		// capturando o produto do banco de dados
		Contato contato = contatos.get();

		// excluindo o produto no banco de dados
		contatoRepository.delete(contato);

		// copiar os dados do produto para o DTO de resposta
		// e retornar os dados (ProdutosGetDTO)
		return modelMapper.map(contato, GetContatoDTO.class);
	}

}
