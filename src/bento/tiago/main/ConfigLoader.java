package bento.tiago.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ConfigLoader {
	
	private static final String ARQUIVO_CONFIG = "config.json";
	private static Config config;
	final static Logger logger = Logger.getLogger(ConfigLoader.class);
	
	public static Config getConfig(){
		if(config==null){
			BufferedReader br = null;
			try{
				File arquivoConfig = FileUtil.getArquivo("./", ARQUIVO_CONFIG);
				br = new BufferedReader(new FileReader(arquivoConfig));
				config = new Gson().fromJson(br, Config.class);
				config.getPastaCaixas();
				br.close();
			} catch (Exception e){
				logger.info("Arquivo de configuração não encontrado. Carregando configuração padrão.");
				config = criarConfiguracaoPadrao();
			} finally {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("Erro ao fechar arquivo", e);
				}
			}
			
			String caminhoCaixas = FileUtil.getPasta(config.getPastaCaixas()).getAbsolutePath();
			EnumCaixas.setCaminhoCaixas(caminhoCaixas);
			
			EnumCaixas.DIARIA.setMaxLeituras(config.getMaxLeituraDiaria());
			EnumCaixas.SEMANAL.setMaxLeituras(config.getMaxLeituraSemanal());
			EnumCaixas.QUINZENAL.setMaxLeituras(config.getMaxLeituraQuinzenal());
			EnumCaixas.MENSAL.setMaxLeituras(config.getMaxLeituraMensal());
			EnumCaixas.SEMESTRAL.setMaxLeituras(config.getMaxLeituraSemestral());
			EnumCaixas.ANUAL.setMaxLeituras(config.getMaxLeituraAnual());
			
		}
		return config;
	}
	
	private static Config criarConfiguracaoPadrao(){
		Config config = new Config();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonConfig = gson.toJson(config);
		
		try {
			File arquivoConfig = FileUtil.getArquivo("./", ARQUIVO_CONFIG);
			BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoConfig));
			bw.write(jsonConfig);
			bw.close();
		} catch (IOException e) {
			logger.error("Erro ao tentar gravar arquivo de configuração padrão.", e);
		}
		
		return config;
	}
}
