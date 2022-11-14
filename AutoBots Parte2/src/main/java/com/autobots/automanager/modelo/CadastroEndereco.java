package com.autobots.automanager.modelo;

import lombok.Data;

@Data
public class CadastroEndereco {
	private long idCliente;
	private String estado;
	private String cidade;
	private String bairro;
	private String rua;
	private String numero;
	private String codigoPostal;
	private String complemento;

}
