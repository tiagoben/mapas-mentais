package bento.tiago.main;

import java.time.LocalDate;

public class Materia {
	private String nome;
	private int qtdLeitura;
	private LocalDate dataUltimaLeitura;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getQtdLeitura() {
		return qtdLeitura;
	}

	public void setQtdLeitura(int qtdLeitura) {
		this.qtdLeitura = qtdLeitura;
	}

	public LocalDate getDataUltimaLeitura() {
		return dataUltimaLeitura;
	}

	public void setDataUltimaLeitura(LocalDate dataUltimaLeitura) {
		this.dataUltimaLeitura = dataUltimaLeitura;
	}

}
