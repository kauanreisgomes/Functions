package com.functions.models;

/**
 * @apiNote Classe para o tipo ComboBox JavaFX
 */
public class Combobox {
	private int id;
	private String nome, dadoextra;
	
	public Combobox(int id, String nome){
		super();
		this.id = id;
		this.nome = nome;
	}
	
	public String getDadoextra() {
		return dadoextra;
	}

	public void setDadoextra(String dadoextra) {
		this.dadoextra = dadoextra;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return nome;
	}
	
	
	
}
