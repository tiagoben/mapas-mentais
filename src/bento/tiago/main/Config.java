package bento.tiago.main;

public class Config {
	private String pastaMetadados = "./metadados";
	private String pastaEntrada = "./entrada";
	private String pastaOutrosArquivos = "./outros";
	private String pastaSaida = "./saida";
	private String pastaCaixas = "./";
	private String arquivoCatalogo = "catalogo.txt";
	private String prefixoArquivoSaida = "";
	private boolean apenasUmArquivoSaida = true;
	private int maxLeituraDiaria = EnumCaixas.DIARIA.getMaxLeituras();
	private int maxLeituraSemanal = EnumCaixas.SEMANAL.getMaxLeituras();
	private int maxLeituraQuinzenal = EnumCaixas.QUINZENAL.getMaxLeituras();
	private int maxLeituraMensal = EnumCaixas.MENSAL.getMaxLeituras();
	private int maxLeituraSemestral = EnumCaixas.SEMESTRAL.getMaxLeituras();
	private int maxLeituraAnual = EnumCaixas.ANUAL.getMaxLeituras();

	public String getPastaMetadados() {
		return pastaMetadados;
	}
	public void setPastaMetadados(String pastaMetadados) {
		this.pastaMetadados = pastaMetadados;
	}
	public String getPastaEntrada() {
		return pastaEntrada;
	}
	public void setPastaEntrada(String pastaEntrada) {
		this.pastaEntrada = pastaEntrada;
	}
	public String getPastaOutrosArquivos() {
		return pastaOutrosArquivos;
	}
	public void setPastaOutrosArquivos(String pastaOutrosArquivos) {
		this.pastaOutrosArquivos = pastaOutrosArquivos;
	}
	public String getPastaSaida() {
		return pastaSaida;
	}
	public void setPastaSaida(String pastaSaida) {
		this.pastaSaida = pastaSaida;
	}
	public String getPastaCaixas() {
		return pastaCaixas;
	}
	public void setPastaCaixas(String pastaCaixas) {
		this.pastaCaixas = pastaCaixas;
	}
	public String getArquivoCatalogo() {
		return arquivoCatalogo;
	}
	public void setArquivoCatalogo(String arquivoCatalogo) {
		this.arquivoCatalogo = arquivoCatalogo;
	}
	public String getPrefixoArquivoSaida() {
		return prefixoArquivoSaida;
	}
	public void setPrefixoArquivoSaida(String prefixoArquivoSaida) {
		this.prefixoArquivoSaida = prefixoArquivoSaida;
	}
	public boolean isApenasUmArquivoSaida() {
		return apenasUmArquivoSaida;
	}
	public void setApenasUmArquivoSaida(boolean apenasUmArquivoSaida) {
		this.apenasUmArquivoSaida = apenasUmArquivoSaida;
	}
	public int getMaxLeituraDiaria() {
		return maxLeituraDiaria;
	}
	public void setMaxLeituraDiaria(int maxLeituraDiaria) {
		this.maxLeituraDiaria = maxLeituraDiaria;
	}
	public int getMaxLeituraSemanal() {
		return maxLeituraSemanal;
	}
	public void setMaxLeituraSemanal(int maxLeituraSemanal) {
		this.maxLeituraSemanal = maxLeituraSemanal;
	}
	public int getMaxLeituraQuinzenal() {
		return maxLeituraQuinzenal;
	}
	public void setMaxLeituraQuinzenal(int maxLeituraQuinzenal) {
		this.maxLeituraQuinzenal = maxLeituraQuinzenal;
	}
	public int getMaxLeituraMensal() {
		return maxLeituraMensal;
	}
	public void setMaxLeituraMensal(int maxLeituraMensal) {
		this.maxLeituraMensal = maxLeituraMensal;
	}
	public int getMaxLeituraSemestral() {
		return maxLeituraSemestral;
	}
	public void setMaxLeituraSemestral(int maxLeituraSemestral) {
		this.maxLeituraSemestral = maxLeituraSemestral;
	}
	public int getMaxLeituraAnual() {
		return maxLeituraAnual;
	}
	public void setMaxLeituraAnual(int maxLeituraAnual) {
		this.maxLeituraAnual = maxLeituraAnual;
	}
}
