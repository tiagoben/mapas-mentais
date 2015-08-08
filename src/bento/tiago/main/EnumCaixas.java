package bento.tiago.main;

import java.io.File;
import java.util.ArrayList;

public enum EnumCaixas {
	DIARIA(1, 30),
	SEMANAL(7, 4),
	QUINZENAL(15, 4),
	MENSAL(30, 6),
	SEMESTRAL(180, 2),
	ANUAL(365, 100);
	
	private String nome = this.toString();
	private ArrayList<Materia> materias = new ArrayList<Materia>();;
	private static String caminhoCaixas;
	private String arquivoMaterias = nome.toLowerCase()+".json";
	private int intervaloDias;
	private int maxLeituras;
	
	EnumCaixas(int intervaloDias, int maxLeituras){
		this.intervaloDias = intervaloDias;
		this.maxLeituras = maxLeituras;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public static String getCaminhoCaixas() {
		return caminhoCaixas;
	}

	public static void setCaminhoCaixas(String caminhoCaixas) {
		EnumCaixas.caminhoCaixas = caminhoCaixas;
	}

	public File getPasta(){
		String caminho = getCaminhoCaixas() + "/" + getNome().toLowerCase();
		File pasta = FileUtil.getPasta(caminho);
		return pasta;
	}

	public ArrayList<Materia> getListaMaterias() {
		return materias;
	}
	
	public boolean addMateria(Materia m){
		return materias.add(m);
	}
	
	public boolean removeMateria(Materia m){
		return materias.remove(m);
	}
	
	public File formarPastaMateria(String nome){
		String caminho = getPasta()+"\\"+nome;
		File pastaFormada = FileUtil.getPasta(caminho);
		return pastaFormada;
	}	

	public String getArquivoMaterias() {
		return arquivoMaterias;
	}

	public void setArquivoMaterias(String arquivoMaterias) {
		this.arquivoMaterias = arquivoMaterias;
	}

	public int getIntervaloDias() {
		return intervaloDias;
	}

	public int getMaxLeituras() {
		return maxLeituras;
	}

	public void setMaxLeituras(int maxLeituras) {
		if(maxLeituras>0){
			this.maxLeituras = maxLeituras;
		}
	}

	
}
