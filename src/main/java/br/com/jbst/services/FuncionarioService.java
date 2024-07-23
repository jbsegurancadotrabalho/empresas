package br.com.jbst.services;
import java.time.Instant;




import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.jbst.DTOS.GetEmpresa1DTO;
import br.com.jbst.DTOS.GetEmpresa3DTO;
import br.com.jbst.DTOS.GetFuncaoDTO;
import br.com.jbst.DTOS.GetFuncionario1DTO;
import br.com.jbst.DTOS.GetFuncionarioDTO;
import br.com.jbst.DTOS.PostFuncionarioDTO;
import br.com.jbst.DTOS.PutFuncionarioDTO;
import br.com.jbst.entities.Empresa;
import br.com.jbst.entities.Funcao;
import br.com.jbst.entities.Funcionario;
import br.com.jbst.repositories.EmpresaRepository;
import br.com.jbst.repositories.FuncaoRepository;
import br.com.jbst.repositories.FuncionarioRepository;

@Service
public class FuncionarioService {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	FuncionarioRepository funcionarioRepository;

	@Autowired
	EmpresaRepository empresaRepository;

	@Autowired
	FuncaoRepository funcaoRepository;

	public GetFuncionarioDTO criarFuncionario(PostFuncionarioDTO dto) throws Exception {
	    try {
	        // Verifica se já existe um funcionário com o mesmo CPF
	        if (funcionarioRepository.existsByCpf(dto.getCpf())) {
	            throw new Exception("CPF já cadastrado para outro funcionário por favor tente outro ! Caso tenha cadastrado o outro colaborador com CPF errado faça a Edição dele.");
	        }

	        // Verifica se já existe um funcionário com o mesmo RG
	        if (funcionarioRepository.existsByRg(dto.getRg())) {
	            throw new Exception("RG já cadastrado para outro funcionário.");
	        }

	        Funcionario funcionario = modelMapper.map(dto, Funcionario.class);
	        funcionario.setIdFuncionario(UUID.randomUUID());
	        funcionario.setDataHoraCriacao(Instant.now());
	        funcionario.setEmpresa(empresaRepository.findById(dto.getIdEmpresa()).orElse(null));
	        funcionario.setFuncao(funcaoRepository.findById(dto.getIdFuncao()).orElse(null));

	        // Verifica se a empresa e a função existem antes de salvar o funcionário
	        if (funcionario.getEmpresa() == null || funcionario.getFuncao() == null) {
	            throw new Exception("Empresa ou função não encontrada.");
	        }

	        funcionarioRepository.save(funcionario);

	        return modelMapper.map(funcionario, GetFuncionarioDTO.class);
	    } catch (Exception e) {
	        // Lidar com exceções (log, lançar, etc.)
	        e.printStackTrace();
	        throw e;
	    }
	}


	public GetFuncionarioDTO editarFuncionario(PutFuncionarioDTO dto) throws Exception {
	    // Verificar se o funcionário existe pelo ID fornecido
	    Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(dto.getIdFuncionario());
	    if (funcionarioOptional.isEmpty()) {
	        throw new IllegalArgumentException("Funcionário inválido: " + dto.getIdFuncionario());
	    }

	    // Obtendo o funcionário do Optional
	    Funcionario funcionario = funcionarioOptional.get();

	    // Mapeando os dados do DTO para a entidade Funcionario
	    funcionario.setNome(dto.getNome());
	    funcionario.setCpf(dto.getCpf());
	    funcionario.setRg(dto.getRg());
	    funcionario.setStatus(dto.getStatus());
	    funcionario.setMatricula(dto.getMatricula());
        funcionario.setFuncao(funcaoRepository.findById(dto.getIdFuncao()).orElse(null));
	    funcionarioRepository.save(funcionario);

	    // Mapeando os dados atualizados do funcionário para o DTO de resposta
	    return modelMapper.map(funcionario, GetFuncionarioDTO.class);
	}

	public GetFuncionarioDTO incluirAssinatura(UUID id, byte[] assinatura) throws Exception {

		// verificando se o funcionario existe (baseado no ID informado)
		Optional<Funcionario> registro = funcionarioRepository.findById(id);
		if (registro.isEmpty())
			throw new IllegalArgumentException("Funcionario inválido: " + id);

		// capturando o funcionario do banco de dados
		Funcionario funcionario = registro.get();

		// alterando a foto
		funcionario.setAssinatura(assinatura);

		// salvando no banco de dados
		funcionarioRepository.save(funcionario);

		// copiar os dados do funcionario para o DTO de resposta
		// e retornar os dados (FuncionarioGetDTO)
		return modelMapper.map(funcionarioRepository.findAll(), GetFuncionarioDTO.class);
	}
	public ResponseEntity<List<GetFuncionarioDTO>> consultarFuncionarios() {

		try {
			List<Funcionario> funcionarios = funcionarioRepository.findAll();
			List<GetFuncionarioDTO> lista = new ArrayList<GetFuncionarioDTO>();

			for (Funcionario funcionario : funcionarios) {
				GetFuncionarioDTO dto = new GetFuncionarioDTO();

		   dto.setIdFuncionario(funcionario.getIdFuncionario());
           dto.setIdEmpresa(funcionario.getEmpresa().getIdEmpresa());
		    dto.setIdFuncao(funcionario.getFuncao().getIdFuncao());
           dto.setDataHoraCriacao(funcionario.getDataHoraCriacao());
           dto.setNome(funcionario.getNome());
		   dto.setCpf(funcionario.getCpf());
           dto.setRg(funcionario.getRg());
				lista.add(dto);
			}

			return ResponseEntity.status(200).body(lista);
		} catch (Exception e) {
			// HTTP 500 (INTERNAL SERVER ERROR)
			return ResponseEntity.status(500).body(null);
		}
	}

	
	
	public List<GetFuncionario1DTO> buscarFuncionarioEmpresas() throws Exception {
		List<Funcionario> funcionario = funcionarioRepository.findAll();
		List<GetFuncionario1DTO> lista = modelMapper.map(funcionario, new TypeToken<List<GetFuncionario1DTO>>() {
		}.getType());
		return lista;
	}
	
	public GetFuncionario1DTO buscarFuncionarioEmpresasPorID(UUID id) throws Exception {
		Optional<Funcionario> documentoOptional = funcionarioRepository.findById(id);
		if (documentoOptional.isPresent()) {
			Funcionario funcionario = documentoOptional.get();
			return modelMapper.map(funcionario, GetFuncionario1DTO.class);
		} else {
			throw new Exception("Documento não encontrado com o ID: " + id);
		}
	}
	
	 public GetFuncionarioDTO getById(UUID idFuncionario) throws Exception {
	        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(idFuncionario);
	        
	        if (funcionarioOptional.isPresent()) {
	            Funcionario funcionario = funcionarioOptional.get();
	            
	            GetFuncionarioDTO dto = new GetFuncionarioDTO();
	            dto.setIdEmpresa(funcionario.getEmpresa().getIdEmpresa()); // Assume que a associação empresa está mapeada corretamente
	            dto.setIdFuncao(funcionario.getFuncao().getIdFuncao()); // Assume que a associação funcao está mapeada corretamente
	            dto.setIdFuncionario(funcionario.getIdFuncionario());
	            dto.setDataHoraCriacao(funcionario.getDataHoraCriacao());
	            dto.setNome(funcionario.getNome());
	            dto.setCpf(funcionario.getCpf());
	            dto.setRg(funcionario.getRg());
	            dto.setStatus(funcionario.getStatus());
	            dto.setMatricula(funcionario.getMatricula());
	            dto.setAssinatura(funcionario.getAssinatura());

	            // Você pode preencher o DTO com os dados da função aqui
	            // Supondo que você tenha um método para mapear uma entidade Funcionario para GetFuncaoDTO
	            dto.setFuncao(mapFuncionarioToGetFuncaoDTO(funcionario.getFuncao()));

	            return dto;
	        } else {
	            // Se o funcionário não for encontrado, você pode lidar com isso como preferir
	            return null; // Ou lançar uma exceção, retornar um DTO de erro, etc.
	        }
	    }
	    
	    // Suponhamos que você tenha um método para mapear uma entidade Funcionario para GetFuncaoDTO
	    private GetFuncaoDTO mapFuncionarioToGetFuncaoDTO(Funcao funcao) {
	        if (funcao != null) {
	            GetFuncaoDTO funcaoDTO = new GetFuncaoDTO();
	            funcaoDTO.setIdFuncao(funcao.getIdFuncao());
	            // Preencha outros campos do DTO de função aqui, se necessário
	            return funcaoDTO;
	        } else {
	            return null;
	        }
	    }
	
	public GetFuncionarioDTO excluirFuncionario(UUID id) throws Exception  {
		Optional<Funcionario> funcionarios = funcionarioRepository.findById(id);
		if (funcionarios.isEmpty())
			throw new IllegalArgumentException("Funcionario não existe: " + id);
		// capturando o funcionario do banco de dados
		Funcionario funcionario = funcionarios.get();
		// excluindo o funcionario no banco de dados
		funcionarioRepository.delete(funcionario);
		// copiar os dados do produto para o DTO de resposta
		// e retornar os dados (ProdutosGetDTO)
		return modelMapper.map(funcionario, GetFuncionarioDTO.class);
	}
	
	 public List<GetFuncionarioDTO> buscarFuncionariosPorIdUsuario(UUID idUsuario) {
	        List<GetFuncionarioDTO> funcionariosDTO = new ArrayList<>();

	        List<Empresa> empresas = empresaRepository.findByUsuario_Id(idUsuario);
	        for (Empresa empresa : empresas) {
	            List<Funcionario> funcionarios = empresa.getFuncionarios();
	            for (Funcionario funcionario : funcionarios) {
	                GetFuncionarioDTO funcionarioDTO = modelMapper.map(funcionario, GetFuncionarioDTO.class);
	                // Mapeie outros dados do funcionário, se necessário
	                funcionariosDTO.add(funcionarioDTO);
	            }
	        }

	        return funcionariosDTO;
	    }



}
