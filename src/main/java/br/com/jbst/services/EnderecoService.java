package br.com.jbst.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jbst.DTOS.GetEnderecoDTO;
import br.com.jbst.DTOS.PostEnderecoDTO;
import br.com.jbst.DTOS.PutEnderecoDTO;
import br.com.jbst.entities.Endereco;
import br.com.jbst.repositories.EnderecoRepository;

import org.modelmapper.ModelMapper; // Importe o ModelMapper

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.security.auth.login.AccountNotFoundException;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ModelMapper modelMapper; // Injete o ModelMapper

    public GetEnderecoDTO criarEndereco(PostEnderecoDTO enderecoDTO) {
    	// Cria um novo UUID para o ID do Endereco
        UUID idEndereco = UUID.randomUUID();

        // Crie a entidade Endereco com o ID atribuído manualmente
        Endereco endereco = modelMapper.map(enderecoDTO, Endereco.class);
        endereco.setIdEndereco(idEndereco);
        endereco.setDataHoraCriacao(Instant.now());

        // Salva o endereço no banco de dados
        Endereco enderecoSalvo = enderecoRepository.save(endereco);

        return modelMapper.map(enderecoSalvo, GetEnderecoDTO.class);
    }

    public GetEnderecoDTO editarEndereco(PutEnderecoDTO enderecoDTO) {
        UUID id = enderecoDTO.getIdEndereco();
        Endereco enderecoExistente = enderecoRepository.findById(id)
                .orElseThrow();

        // Atualiza os campos do endereçoExistente com base nos valores em enderecoDTO
        enderecoExistente.setIdEndereco(enderecoDTO.getIdEndereco());
        enderecoExistente.setCep(enderecoDTO.getCep());
        enderecoExistente.setLogradouro(enderecoDTO.getLogradouro());
        enderecoExistente.setComplemento(enderecoDTO.getComplemento());
        enderecoExistente.setNumero(enderecoDTO.getNumero());
        enderecoExistente.setBairro(enderecoDTO.getBairro());
        enderecoExistente.setLocalidade(enderecoDTO.getLocalidade());
        enderecoExistente.setUf(enderecoDTO.getUf());
        enderecoExistente.setDataHoraCriacao(Instant.now());

        Endereco enderecoAtualizado = enderecoRepository.save(enderecoExistente);
        return modelMapper.map(enderecoAtualizado, GetEnderecoDTO.class);
    }


    public List<GetEnderecoDTO> consultarEnderecos() {
        List<Endereco> enderecos = enderecoRepository.findAll();
        return enderecos.stream()
                .map(endereco -> modelMapper.map(endereco, GetEnderecoDTO.class))
                .collect(Collectors.toList());
    }


    public GetEnderecoDTO consultarUmEndereco(UUID id) throws AccountNotFoundException {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Endereço não encontrado com ID: " + id));
        return modelMapper.map(endereco, GetEnderecoDTO.class);
    }


    public void excluirEndereco(UUID id) throws AccountNotFoundException {
        if (!enderecoRepository.existsById(id)) {
            throw new AccountNotFoundException("Endereço não encontrado com ID: " + id);
        }
        enderecoRepository.deleteById(id);
    }

}

