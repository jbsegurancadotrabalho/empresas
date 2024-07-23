package br.com.jbst.services;

import java.io.ByteArrayInputStream;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jbst.DTOS.GetEmpresa1DTO;
import br.com.jbst.DTOS.GetEmpresa2DTO;
import br.com.jbst.DTOS.GetEmpresa3DTO;
import br.com.jbst.DTOS.GetEmpresaDTO;
import br.com.jbst.DTOS.PostEmpresaDTO;
import br.com.jbst.DTOS.PostEmpresaUsuariosDTO;
import br.com.jbst.DTOS.PutEmpresaDTO;
import br.com.jbst.DTOS.PutEmpresaUsuariosDTO;
import br.com.jbst.entities.Empresa;
import br.com.jbst.entities.Usuario;
import br.com.jbst.reports.EmpresaReports;
import br.com.jbst.repositories.EmpresaRepository;
import br.com.jbst.repositories.IUsuarioRepository;

@Service
public class EmpresaService {
	

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	EmpresaRepository empresaRepository;	
	
	@Autowired
	IUsuarioRepository usuarioRepository;
	
	@Autowired
	EmpresaReports empresaReports;

	
	public GetEmpresaDTO criarEmpresa(PostEmpresaDTO dto) throws Exception {
	    try {
	        // Verifica se já existe uma empresa com o mesmo CNPJ
	        if (empresaRepository.existsByCnpj(dto.getCnpj())) {
	            throw new Exception("CNPJ já cadastrado para outra empresa.");
	        }

	        // Verifica se já existe uma empresa com a mesma razão social
	        if (empresaRepository.existsByRazaoSocial(dto.getRazaosocial())) {
	            throw new Exception("Razão social já cadastrada para outra empresa.");
	        }

	        // Verifica se já existe uma empresa com o mesmo nome fantasia
	        if (empresaRepository.existsByNomeFantasia(dto.getNomefantasia())) {
	            throw new Exception("Nome fantasia já cadastrado para outra empresa.");
	        }

	        Empresa empresa = modelMapper.map(dto, Empresa.class);
	        empresa.setIdEmpresa(UUID.randomUUID());
	        empresa.setDataHoraCriacao(Instant.now());
	        
	        
	        Usuario usuario = usuarioRepository.findById(dto.getId()).orElseThrow(() -> new Exception("Usuário não encontrado"));
	        empresa.setUsuario(usuario);
	        
	        empresaRepository.save(empresa);

	        return modelMapper.map(empresa, GetEmpresaDTO.class);
	    } catch (Exception e) {
	        // Lidar com exceções (log, lançar, etc.)
	        e.printStackTrace();
	        throw e;
	    }
	}

	
	public GetEmpresaDTO criarEmpresaCliente(PostEmpresaUsuariosDTO dto) throws Exception {
	    try {
	        // Verifica se já existe uma empresa com o mesmo CNPJ
	        if (empresaRepository.existsByCnpj(dto.getCnpj())) {
	            throw new Exception("CNPJ já cadastrado para outra empresa.");
	        }

	        // Verifica se já existe uma empresa com a mesma razão social
	        if (empresaRepository.existsByRazaoSocial(dto.getRazaosocial())) {
	            throw new Exception("Razão social já cadastrada para outra empresa.");
	        }

	        // Verifica se já existe uma empresa com o mesmo nome fantasia
	        if (empresaRepository.existsByNomeFantasia(dto.getNomefantasia())) {
	            throw new Exception("Nome fantasia já cadastrado para outra empresa.");
	        }

	        Empresa empresa = modelMapper.map(dto, Empresa.class);
	        empresa.setIdEmpresa(UUID.randomUUID());
	        empresa.setDataHoraCriacao(Instant.now());
	        
	        
	        Usuario usuario = usuarioRepository.findById(dto.getId()).orElseThrow(() -> new Exception("Usuário não encontrado"));
	        empresa.setUsuario(usuario);
	        
	        empresaRepository.save(empresa);

	        return modelMapper.map(empresa, GetEmpresaDTO.class);
	    } catch (Exception e) {
	        // Lidar com exceções (log, lançar, etc.)
	        e.printStackTrace();
	        throw e;
	    }
	}


	public GetEmpresaDTO editarEmpresa(PutEmpresaDTO dto) throws Exception {
	    Optional<Empresa> empresaOptional = empresaRepository.findById(dto.getIdEmpresa());
	    if (empresaOptional.isEmpty()) {
	        throw new IllegalArgumentException("Empresa não encontrada com ID: " + dto.getIdEmpresa());
	    }

	    Empresa empresa = empresaOptional.get();

	    // Salvando a data e hora de criação atual
	    Instant dataHoraCriacaoAtual = empresa.getDataHoraCriacao();

	    // Mapeando os dados do DTO para a entidade, exceto a dataHoraCriacao
	    modelMapper.map(dto, empresa);

	    // Restaurando a data e hora de criação original
	    empresa.setDataHoraCriacao(dataHoraCriacaoAtual);

	    // Obtendo o usuário da base de dados e atribuindo à empresa
	    Usuario usuario = usuarioRepository.findById(dto.getId()).orElseThrow(() -> new Exception("Usuário não encontrado"));
	    empresa.setUsuario(usuario);

	    // Salvando as alterações da empresa
	    empresaRepository.save(empresa);

	    return modelMapper.map(empresa, GetEmpresaDTO.class);
	}
	
	public GetEmpresaDTO editarEmpresaUsuarios(PutEmpresaUsuariosDTO dto) throws Exception {
	    Optional<Empresa> empresaOptional = empresaRepository.findById(dto.getIdEmpresa());
	    if (empresaOptional.isEmpty()) {
	        throw new IllegalArgumentException("Empresa não encontrada com ID: " + dto.getIdEmpresa());
	    }

	    Empresa empresa = empresaOptional.get();

	    // Salvando a data e hora de criação atual
	    Instant dataHoraCriacaoAtual = empresa.getDataHoraCriacao();

	    // Mapeando os dados do DTO para a entidade, exceto a dataHoraCriacao
	    modelMapper.map(dto, empresa);

	    // Restaurando a data e hora de criação original
	    empresa.setDataHoraCriacao(dataHoraCriacaoAtual);

	  

	    // Salvando as alterações da empresa
	    empresaRepository.save(empresa);

	    return modelMapper.map(empresa, GetEmpresaDTO.class);
	}

	public List<GetEmpresa1DTO> buscarEmpresaFuncionario() throws Exception {
		List<Empresa> empresa = empresaRepository.findAll();
		List<GetEmpresa1DTO> lista = modelMapper.map(empresa, new TypeToken<List<GetEmpresa1DTO>>() {
		}.getType());
		return lista;
	}
	
	
	
	public List<GetEmpresaDTO> getAll(String nome) throws Exception {

		List<Empresa> empresa = empresaRepository.findAll();
		List<GetEmpresaDTO> lista = modelMapper.map(empresa, new TypeToken<List<GetEmpresaDTO>>() {
		}.getType());
		return lista;
	}

	public List<GetEmpresa3DTO> getAllFunc(String nome) throws Exception {
		List<Empresa> empresa = empresaRepository.findAll();
		List<GetEmpresa3DTO> lista = modelMapper.map(empresa, new TypeToken<List<GetEmpresa3DTO>>() {
		}.getType());
		return lista;
	}
	
	

	
	public GetEmpresa3DTO getById(UUID id) throws Exception {
		// consultando o produto através do ID
		Empresa empresa = empresaRepository.find(id);
				if (empresa == null)
					throw new IllegalArgumentException("Chamados não encontrado: " + id);

				// retornando os dados
				return modelMapper.map(empresa, GetEmpresa3DTO.class);
			}
	
	public GetEmpresa2DTO consultarFuncionarioDaEmpresaPorId(UUID id) throws Exception {
		// consultando o produto através do ID
		Empresa empresa = empresaRepository.find(id);
				if (empresa == null)
					throw new IllegalArgumentException("Empresa não encontrada: " + id);

				// retornando os dados
				return modelMapper.map(empresa, GetEmpresa2DTO.class);
			}
	

	public GetEmpresaDTO excluirEmpresa(UUID id) throws Exception  {

		Optional<Empresa> empresas = empresaRepository.findById(id);
		if (empresas.isEmpty())
			throw new IllegalArgumentException("Chamado inválido: " + id);

		// capturando o produto do banco de dados
		Empresa empresa = empresas.get();

		// excluindo o produto no banco de dados
		empresaRepository.delete(empresa);

		// copiar os dados do produto para o DTO de resposta
		// e retornar os dados (ProdutosGetDTO)
		return modelMapper.map(empresa, GetEmpresaDTO.class);
	}
	
	
	
	public  byte[] getReport() throws Exception {

		//consultando todos os produtos no banco de dados
		List<Empresa> empresas = empresaRepository.findAll();
		
		//gerando o relatório em PDF
		ByteArrayInputStream stream = empresaReports.createPdf(empresas);
		
		//retornando o relatório em bytes
		return stream.readAllBytes();
	}
	
	
	 public List<GetEmpresaDTO> buscarEmpresasPorIdUsuario(UUID idUsuario) {
	        List<Empresa> empresas = empresaRepository.findByUsuario_Id(idUsuario);
	        List<GetEmpresaDTO> lista = modelMapper.map(empresas, new TypeToken<List<GetEmpresaDTO>>() {}.getType());
	        return lista;
	    }
	
	
	
	}


