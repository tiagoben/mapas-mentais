package bento.tiago.main;

import java.util.Calendar;

public class Materia {
	private String nome;
	private int qtdLeitura;
	private Calendar dataUltimaLeitura;

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

	public Calendar getDataUltimaLeitura() {
		return dataUltimaLeitura;
	}

	public void setDataUltimaLeitura(Calendar dataUltimaLeitura) {
		this.dataUltimaLeitura = dataUltimaLeitura;
	}

}
