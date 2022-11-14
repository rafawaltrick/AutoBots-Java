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
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelo.ClienteAtualizador;
import com.autobots.automanager.modelo.DocumentoAtualizador;
import com.autobots.automanager.modelo.DocumentoExcluir;
import com.autobots.automanager.modelo.DocumentoSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.DocumentoRepositorio;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {
	@Autowired
	private DocumentoRepositorio repositorio;
	@Autowired
	private DocumentoSelecionador selecionador;
	@Autowired
	private ClienteRepositorio repositorioCliente;

	@GetMapping("/documentos")
	public List<Documento> buscarDocumento() {
		List<Documento> documentos = repositorio.findAll();
		return documentos;
	}

	@GetMapping("/documento/{id}")
	public Documento obterDocumento(@PathVariable long id) {
		List<Documento> documentos = repositorio.findAll();
		return selecionador.selecionador(documentos, id);
	}

	@PutMapping("/atualizacao")
	public ResponseEntity<Documento> atualizarDocumento(@RequestBody Documento atualizacao) {
		Documento documento = repositorio.getById(atualizacao.getId());
		DocumentoAtualizador atualizador = new DocumentoAtualizador();
		atualizador.atualizar(documento, atualizacao);
		repositorio.save(documento);
		return new ResponseEntity<Documento>(atualizacao, HttpStatus.OK);
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Documento> cadastrarDocumento(@RequestBody Documento documento) {
		repositorio.save(documento);
		return new ResponseEntity<>(documento, HttpStatus.CREATED);
	}

	@DeleteMapping("/excluir")
	public String excluirDocumento(@RequestBody DocumentoExcluir exclusao) {
		Cliente cliente = repositorioCliente.getById(exclusao.getIdCliente());
		Documento alvo = null;
		for(Documento doc: cliente.getDocumentos()) {
			if(doc.getId() == exclusao.getIdDoc()) {
				alvo = doc;
				repositorio.deleteById(doc.getId());
			}
		}
		cliente.getDocumentos().remove(alvo);
		repositorioCliente.save(cliente);
		return "Documento Excluído Com Sucesso!!!";
	}
}
