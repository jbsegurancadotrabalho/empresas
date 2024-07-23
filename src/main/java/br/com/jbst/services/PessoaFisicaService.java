package br.com.jbst.services;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jbst.DTOS.GetEmpresaDTO;
import br.com.jbst.DTOS.GetPessoaFisicaDTO;
import br.com.jbst.DTOS.PostPessoaFisicaDTO;
import br.com.jbst.DTOS.PutPessoaFisicaDTO;
import br.com.jbst.entities.Empresa;
import br.com.jbst.entities.PessoaFisica;
import br.com.jbst.entities.Usuario;
import br.com.jbst.repositories.IUsuarioRepository;
import br.com.jbst.repositories.PessoaFisicaRepository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import br.com.jbst.entities.Usuario;


@Service
public class PessoaFisicaService {
	
	@Autowired
	IUsuarioRepository usuarioRepository;

	private final PessoaFisicaRepository pessoaFisicaRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public PessoaFisicaService(PessoaFisicaRepository pessoaFisicaRepository, ModelMapper modelMapper) {
		this.pessoaFisicaRepository = pessoaFisicaRepository;
		this.modelMapper = modelMapper;
	}

	  public GetPessoaFisicaDTO criarPessoaFisica(PostPessoaFisicaDTO pessoaFisicaDTO) throws Exception {
	        // Verificar se já existe uma pessoa física com o mesmo CPF
	        if (existePessoaFisicaComCpf(pessoaFisicaDTO.getCpf())) {
	            throw new Exception("Já existe uma pessoa física cadastrada com o CPF informado.");
	        }

	        // O CPF é único, continue com o processo de criação
	        PessoaFisica pessoaFisica = modelMapper.map(pessoaFisicaDTO, PessoaFisica.class);
	        pessoaFisica.setIdpessoafisica(UUID.randomUUID());
	        pessoaFisica.setDataHoraCriacao(Instant.now());
	        Usuario usuario = usuarioRepository.findById(pessoaFisicaDTO.getId()).orElseThrow(() -> new Exception("Usuário não encontrado"));
	        pessoaFisica.setUsuario(usuario);
	        
	        pessoaFisicaRepository.save(pessoaFisica);

	        return modelMapper.map(pessoaFisica, GetPessoaFisicaDTO.class);
	    }

	    private boolean existePessoaFisicaComCpf(String cpf) {
	        Optional<PessoaFisica> existingPessoaFisica = pessoaFisicaRepository.findByCpf(cpf);
	        return existingPessoaFisica.isPresent();
	    }

	public GetPessoaFisicaDTO editarPessoaFisica(PutPessoaFisicaDTO dto) throws Exception {
		UUID id = dto.getIdpessoafisica();
		PessoaFisica pessoaFisica = pessoaFisicaRepository.findById(id).orElseThrow();
		modelMapper.map(dto, pessoaFisica);
		pessoaFisica.setDataHoraCriacao(Instant.now());
	    Usuario usuario = usuarioRepository.findById(dto.getId()).orElseThrow(() -> new Exception("Usuário não encontrado"));
        pessoaFisica.setUsuario(usuario);
		pessoaFisicaRepository.save(pessoaFisica);
		return modelMapper.map(pessoaFisica, GetPessoaFisicaDTO.class);
	}

	public List<GetPessoaFisicaDTO> consultarPessoaFisica() {
		List<PessoaFisica> pessoasFisicas = pessoaFisicaRepository.findAll();
		return pessoasFisicas.stream().map(pf -> modelMapper.map(pf, GetPessoaFisicaDTO.class))
				.collect(Collectors.toList());
	}

	public GetPessoaFisicaDTO consultarUmaPessoaFisica(UUID id) {
		Optional<PessoaFisica> pessoaFisicaOptional = pessoaFisicaRepository.findById(id);
		if (pessoaFisicaOptional.isEmpty()) {
			throw new RuntimeException("Pessoa física não encontrada"); // Lançar exceção quando não encontrada
		}
		PessoaFisica pessoaFisica = pessoaFisicaOptional.get();
		return modelMapper.map(pessoaFisica, GetPessoaFisicaDTO.class);
	}

	public void excluirPessoaFisica(UUID id) {
		Optional<PessoaFisica> pessoaFisicaOptional = pessoaFisicaRepository.findById(id);
		if (pessoaFisicaOptional.isEmpty()) {
			throw new RuntimeException("Pessoa física não encontrada"); // Lançar exceção quando não encontrada
		}
		try {
			pessoaFisicaRepository.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeException("Pessoa física não encontrada"); // Lançar exceção quando não encontrada
		}
	}
	
	public GetPessoaFisicaDTO incluirAssinatura(UUID id, byte[] assinatura) throws Exception {
		Optional<PessoaFisica> registro = pessoaFisicaRepository.findById(id);
		if (registro.isEmpty())
			throw new IllegalArgumentException("Pessoa Fisica não Existe inválido: " + id);
		PessoaFisica pessoafisica = registro.get();
		pessoafisica.setAssinatura_pessoafisica(assinatura);
		pessoaFisicaRepository.save(pessoafisica);
		return modelMapper.map(pessoafisica, GetPessoaFisicaDTO.class);
	}
	
	 public List<GetPessoaFisicaDTO> buscarPessoaFisicaPorIdUsuario(UUID idUsuario) {
	        List<PessoaFisica> empresas = pessoaFisicaRepository.findByUsuario_Id(idUsuario);
	        List<GetPessoaFisicaDTO> lista = modelMapper.map(empresas, new TypeToken<List<GetPessoaFisicaDTO>>() {}.getType());
	        return lista;
	    }

}
