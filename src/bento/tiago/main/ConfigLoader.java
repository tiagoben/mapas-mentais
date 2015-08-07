package bento.tiago.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ConfigLoader {
	
	private static final String ARQUIVO_CONFIG = "config.json";
	private static Config config;
	
	public static Config getConfig(){
		if(config==null){
			try{
				BufferedReader br;
				br = new BufferedReader(new FileReader(ARQUIVO_CONFIG));
				config = new Gson().fromJson(br, Config.class);
				br.close();
			} catch (Exception e){
				config = criarConfiguracaoPadrao();
			}
			
			EnumCaixas.setCaminhoCaixas(config.getPastaCaixas());
			
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
			BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_CONFIG));
			bw.write(jsonConfig);
			bw.close();
		} catch (IOException e) {
			System.out.println("Erro ao tentar gravar arquivo de configuração padrão.");
			e.printStackTrace();
		}
		
		return config;
	}
}
