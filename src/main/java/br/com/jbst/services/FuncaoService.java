package br.com.jbst.services;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jbst.DTOS.GetFuncaoDTO;
import br.com.jbst.DTOS.PostFuncaoDTO;
import br.com.jbst.DTOS.PutFuncaoDTO;
import br.com.jbst.entities.Funcao;
import br.com.jbst.repositories.FuncaoRepository;

@Service
public class FuncaoService {
	
	@Autowired
	FuncaoRepository funcaoRepository;
	
	
	@Autowired
	ModelMapper modelMapper;
	

	public GetFuncaoDTO criarFuncao(PostFuncaoDTO dto) throws Exception {
	    try {
	    	if (funcaoRepository.existsByFuncao(dto.getFuncao())) {
	    	    throw new Exception("Função já cadastrada.");
	    	}
	    	
	        Funcao funcao = modelMapper.map(dto, Funcao.class);
	        funcao.setIdFuncao(UUID.randomUUID());
	        funcao.setDataHoraCriacao(Instant.now());
	        funcaoRepository.save(funcao);

	        return modelMapper.map(funcao, GetFuncaoDTO.class);
	    } catch (Exception e) {
	        // Lidar com exceções (log, lançar, etc.)
	        e.printStackTrace();
	        throw e;
	    }
	}




	public GetFuncaoDTO editarFuncao(PutFuncaoDTO dto) throws Exception {
	    Optional<Funcao> funcoes = funcaoRepository.findById(dto.getIdFuncao());
	    if (funcoes.isEmpty())
	        throw new IllegalArgumentException("Chamado inválido: " + dto.getIdFuncao());

	    // Retrieve existing Funcao entity
	    Funcao funcao = funcoes.get();

	    // Save existing datahoracriacao
	    Instant datahoracriacao = funcao.getDataHoraCriacao();

	    // Map DTO to Funcao entity
	    funcao = modelMapper.map(dto, Funcao.class);

	    // Set back the existing datahoracriacao
	    funcao.setDataHoraCriacao(datahoracriacao);

	    funcaoRepository.save(funcao);
	    return modelMapper.map(funcaoRepository.find(funcao.getIdFuncao()), GetFuncaoDTO.class);
	}


	public List<GetFuncaoDTO> getAll() throws Exception {

		List<Funcao> funcao = funcaoRepository.findAll();
		List<GetFuncaoDTO> lista = modelMapper.map(funcao, new TypeToken<List<GetFuncaoDTO>>() {
		}.getType());
		return lista;
	}


	public GetFuncaoDTO getById(UUID id) throws Exception {

		// consultando o produto através do ID
		Funcao funcao = funcaoRepository.find(id);
				if (funcao == null)
					throw new IllegalArgumentException("Função não encontrado: " + id);

				// retornando os dados
				return modelMapper.map(funcao, GetFuncaoDTO.class);
			}
	
	public GetFuncaoDTO excluirFuncao(UUID id) throws Exception  {

		Optional<Funcao> funcoes = funcaoRepository.findById(id);
		if (funcoes.isEmpty())
			throw new IllegalArgumentException("Funcao inválida: " + id);

		// capturando o produto do banco de dados
		Funcao funcao = funcoes.get();

		// excluindo o produto no banco de dados
		funcaoRepository.delete(funcao);

		// copiar os dados do produto para o DTO de resposta
		// e retornar os dados (ProdutosGetDTO)
		return modelMapper.map(funcao, GetFuncaoDTO.class);
	}

}
