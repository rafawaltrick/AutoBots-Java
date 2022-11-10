package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.ClienteSelecionador;
import com.autobots.automanager.modelo.TelefoneAtualizador;
import com.autobots.automanager.modelo.TelefoneSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@RestController
@RequestMapping("/telefone")
public class TelefoneControle {
	@Autowired
	private TelefoneRepositorio repositorio;
	@Autowired
	private ClienteRepositorio repositorioCliente;
	@Autowired
	private ClienteSelecionador selecionadorCliente;
	@Autowired
	private TelefoneSelecionador selecionador;
	
	@GetMapping("/telefones")
	public List<Telefone> buscarTelefone() {
		List<Telefone> telefone = repositorio.findAll();
		return telefone;
	}
	
	@GetMapping("/telefone/{id}")
	public Telefone obterTelefone(@PathVariable long id) {
		List<Telefone> telefone = repositorio.findAll();
		return selecionador.selecionar(telefone, id);
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<Telefone> atualizarCliente(@RequestBody Telefone atualizacao) {
		Telefone telefone = repositorio.getById(atualizacao.getId());
		TelefoneAtualizador atualizador = new TelefoneAtualizador();
		atualizador.atualizar(telefone, atualizacao);
		repositorio.save(telefone);
		return new ResponseEntity<Telefone>(atualizacao, HttpStatus.OK);
	}
	
	@PostMapping("/cadastro/{id}")
	public void cadastrarTelefone(@PathVariable Long id, @RequestBody Telefone telefone) {
		List<Cliente> cliente =  repositorioCliente.findAll();
		Cliente alvo = selecionadorCliente.selecionar(cliente, id);
		alvo.getTelefones().add(telefone);
		repositorioCliente.save(alvo);
		repositorio.save(telefone);
		
	}

}
