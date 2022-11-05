package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.EnderecoSelecionador;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {
	@Autowired
	private EnderecoRepositorio repositorio;
	@Autowired
	private EnderecoSelecionador selecionador;

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
}
