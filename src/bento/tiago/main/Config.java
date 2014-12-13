package bento.tiago.main;

import java.io.File;

public class Config {
	private File arquivoCatalogoPastas;
	private File pastaTemporaria;
	private File pastaConfig;

	public File getArquivoCatalogoPastas() {
		return arquivoCatalogoPastas;
	}

	public void setArquivoCatalogoPastas(File arquivoCatalogoPastas) {
		this.arquivoCatalogoPastas = arquivoCatalogoPastas;
	}

	public File getPastaTemporaria() {
		return pastaTemporaria;
	}

	public void setPastaTemporaria(File pastaTemporaria) {
		this.pastaTemporaria = pastaTemporaria;
	}

	public File getPastaConfig() {
		return pastaConfig;
	}

	public void setPastaConfig(File pastaConfig) {
		this.pastaConfig = pastaConfig;
	}	
	
}
