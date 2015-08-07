package bento.tiago.main;

import static bento.tiago.main.FileUtil.getPasta;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Controle {

	public void run() {
		System.out.println("Iniciando a manipulação de Mapas Mentais");
		
		System.out.println("Carregando configurações");		
		Config config = ConfigLoader.getConfig();
		
		if(existeMapaNaPastaDeSaida()){
			System.out.println("Você não leu o seu ultimo mapa!");	
		} else {
			System.out.println("Recebendo mapas");
			
			Entrada.receberMapas();

			InicializadorCaixas.carregarMaterias();
	
			System.out.println("Iniciando transferências entre caixas");
			ControleCaixas.executarTransferencias();
			System.out.println("Transferências entre caixas concluída");
	
			System.out.println("Preparando mapas para o dia");
			MapasDoDia mdd = new MapasDoDia();
			mdd.prepararMapasDoDia(getPasta(config.getPastaSaida()));
	
			System.out.println("\n\nPersistindo dados de configuração");
			Persist.getInstance().salvarTudo();

		}
		System.out.println("Fim da manipulação de Mapas Mentais");
		System.out.println("==================================================================");
	}

	
	
	private boolean existeMapaNaPastaDeSaida(){
		Config config = ConfigLoader.getConfig();
		File pastaSaida = FileUtil.getPasta(config.getPastaSaida());
		
		List<String> arquivos = Arrays.asList(pastaSaida.list());
		
		boolean match = arquivos.stream()
			.anyMatch(arquivo -> 
				arquivo.startsWith(config.getPrefixoArquivoSaida()) 
				&& 
				arquivo.endsWith(".pdf")
			);
		
		return match;
	}

}
