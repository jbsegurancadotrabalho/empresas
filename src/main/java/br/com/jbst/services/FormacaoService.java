package br.com.jbst.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jbst.DTO.GetFormacaoDTO;
import br.com.jbst.DTO.PutFormacaoDTO;
import br.com.jbst.DTO.PostFormacaoDTO;

import br.com.jbst.entities.Formacao;

import br.com.jbst.repositories.FormacaoRepository;
import br.com.jbst.repositories.PessoaFisicaRepository;

@Service
public class FormacaoService {

    @Autowired
    FormacaoRepository formacaoRepository;
    
    @Autowired
    PessoaFisicaRepository pessoaFisicaRepository;
    
    @Autowired
    ModelMapper modelMapper;

  

    public GetFormacaoDTO criarFormacao(PostFormacaoDTO dto) {
    	Formacao formacao = modelMapper.map(dto, Formacao.class);
    	formacao.setIdFormacao(UUID.randomUUID());
    	formacao.setDataHoraCriacao(Instant.now());
    	formacaoRepository.save(formacao);
		return modelMapper.map(formacao, GetFormacaoDTO.class);
	}
    
    public GetFormacaoDTO editarFormacao(PutFormacaoDTO dto) {
		UUID id = dto.getIdFormacao();

		Formacao formacao = formacaoRepository.findById(id).orElseThrow();

		modelMapper.map(dto, formacao);
		dto.setDataHoraCriacao(Instant.now());

		formacaoRepository.save(formacao);
		return modelMapper.map(formacao, GetFormacaoDTO.class);
	}


    public List<GetFormacaoDTO> consultarFormacao() {
		List<Formacao> formacoes = formacaoRepository.findAll();
		return formacoes.stream().map(pf -> modelMapper.map(pf, GetFormacaoDTO.class))
				.collect(Collectors.toList());
	}

    public GetFormacaoDTO consultarUmaFormacaoService(UUID idFormacao) {
        Optional<Formacao> formacaoOptional = formacaoRepository.findById(idFormacao);

        if (formacaoOptional.isPresent()) {
            Formacao formacao = formacaoOptional.get();
            return modelMapper.map(formacao, GetFormacaoDTO.class);
        } else {
          
			throw new RuntimeException("Formação não encontrada"); // Lançar exceção quando não encontrada
        }
    }

    public GetFormacaoDTO excluirFormacao(UUID id) throws Exception  {
		Optional<Formacao> formacoes = formacaoRepository.findById(id);
		if (formacoes.isEmpty())
			throw new IllegalArgumentException("Funcionario não existe: " + id);
		// capturando o funcionario do banco de dados
		Formacao formacao = formacoes.get();
		// excluindo o funcionario no banco de dados
		formacaoRepository.delete(formacao);
		GetFormacaoDTO dto = modelMapper.map(formacao, GetFormacaoDTO.class);
		dto.getFormacao();
		dto.setMensagem("Formação excluída com sucesso!");
	    return dto;

	}

}

