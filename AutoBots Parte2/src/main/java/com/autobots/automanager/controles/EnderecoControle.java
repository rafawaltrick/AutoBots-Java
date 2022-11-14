package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.CadastroEndereco;
import com.autobots.automanager.modelo.ClienteSelecionador;
import com.autobots.automanager.modelo.EnderecoAtualizador;
import com.autobots.automanager.modelo.EnderecoSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {
	@Autowired
	private EnderecoRepositorio repositorio;
	@Autowired
	private EnderecoSelecionador selecionador;
	@Autowired
	private ClienteSelecionador selecionadorCliente;
	@Autowired
	private ClienteRepositorio repositorioCliente;
	

	@GetMapping("/enderecos")
	public List<Endereco> buscaEndereco() {
		List<Endereco> enderecos = repositorio.findAll();
		return enderecos;
	}

	@GetMapping("/endereco/{id}")
	public Endereco obterEndereco(@PathVariable long id) {
		List<Endereco> enderecos = repositorio.findAll();
		return selecionador.selecionar(enderecos, id);
	}
	
	@PostMapping("/cadastro")
	public void cadastrarEndereco(@RequestBody CadastroEndereco dados) {
		Cliente alvo = selecionadorCliente.selecionar(repositorioCliente.findAll(), dados.getIdCliente());
		
		Endereco end = new Endereco();
		end.setEstado(dados.getEstado());
		end.setCidade(dados.getCidade());
		end.setBairro(dados.getBairro());
		end.setRua(dados.getRua());
		end.setNumero(dados.getNumero());
		end.setCodigoPostal(dados.getCodigoPostal());
		end.setInformacoesAdicionais(dados.getComplemento());
		
		alvo.setEndereco(end);
		
		repositorioCliente.save(alvo);
		
	}
	
	@DeleteMapping("/excluir")
	public void excluirEndereco(@RequestBody Cliente exclusao) {
		Cliente cliente = repositorioCliente.getById(exclusao.getId());
		repositorio.delete(cliente.getEndereco());
		cliente.setEndereco(null);
		repositorioCliente.save(cliente);
	}
	
	@PutMapping("/atualizar")
	public void atualizarEndereco(@RequestBody Endereco atualizacao) {
		Endereco endereco = repositorio.getById(atualizacao.getId());
		EnderecoAtualizador atualizador = new EnderecoAtualizador();
		atualizador.atualizar(endereco,  atualizacao);
		repositorio.save(endereco);
		
	}
}
