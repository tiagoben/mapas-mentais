package bento.tiago.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static bento.tiago.main.FileUtil.*;

public class Controle {

	public void run() {
		System.out.println("Iniciando a manipulação de Mapas Mentais");
		
		System.out.println("Carregando configurações");
		Config config = ConfigLoader.getConfig();
		
		System.out.println("Recebendo mapas");
		try {
			new Entrada().receberMapas();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (EnumCaixas caixa : EnumCaixas.values()) {
			InicializadorCaixas.init(caixa);
		}

		System.out.println("Iniciando transferências entre caixas");
		for (int i = 0; i < EnumCaixas.values().length - 1; i++) {
			EnumCaixas caixaOrigem = EnumCaixas.values()[i];
			EnumCaixas caixaDestino = EnumCaixas.values()[i + 1];
			int maxLeituras = caixaOrigem.getMaxLeituras();
			ArrayList<Materia> transferidos = 
					ControleCaixas.transferirMaterias(caixaOrigem, caixaDestino, maxLeituras,true);

			if (transferidos.size() > 0) {

				int countMapas = 0;
				ArrayList<String> nomesPastas = new ArrayList<String>();

				for (Materia m : transferidos) {
					File f = caixaDestino.formarPastaMateria(m.getNome());
					int qtdMapas = f.list().length;

					String s = m.getNome() + " (" + qtdMapas + ")";
					nomesPastas.add(s);

					countMapas = countMapas + qtdMapas;
				}

				Logger.info(countMapas + " mapas em "
						+ nomesPastas.size() + " pastas transferidos de "
						+ caixaOrigem.getNome() + " para "
						+ caixaDestino.getNome());

				for (String s : nomesPastas) {
					Logger.info("\t" + s);
				}
				Logger.info("\n");
			}
		}
		System.out.println("Transferências entre caixas concluída");

		System.out.println("Preparando mapas para o dia");
		MapasDoDia mdd = new MapasDoDia();
		mdd.prepararMapasDoDia(getPasta(config.getPastaSaida()));

		System.out.println("\n\nPersistindo dados de configuração");
		Persist.getInstance().salvarTudo();

		System.out.println("Fim da manipulação de Mapas Mentais");
		System.out.println("==================================================================");
	}

}
