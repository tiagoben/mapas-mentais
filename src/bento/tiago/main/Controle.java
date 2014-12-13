package bento.tiago.main;

import java.io.File;
import java.util.ArrayList;

public class Controle {

	public void run() {
		System.out.println("Iniciando a manipulação de Mapas Mentais");
		
		System.out.println("Carregando configurações");
		Config config = StubConfig.getConfig();

		for (EnumCaixas caixa : EnumCaixas.values()) {
			InicializadorCaixas.init(caixa);
		}

		System.out.println("Iniciando transferências entre caixas");
		for (int i = 0; i < EnumCaixas.values().length - 1; i++) {
			EnumCaixas caixaOrigem = EnumCaixas.values()[i];
			EnumCaixas caixaDestino = EnumCaixas.values()[i + 1];
			int maxLeituras = caixaOrigem.getMaxLeituras();
			ArrayList<Materia> transferidos = ControleCaixas
					.transferirMaterias(caixaOrigem, caixaDestino, maxLeituras,
							true);

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

				System.out.println(countMapas + " mapas em "
						+ nomesPastas.size() + " pastas transferidos de "
						+ caixaOrigem.getNome() + " para "
						+ caixaDestino.getNome());

				for (String s : nomesPastas) {
					System.out.println("\t" + s);
				}
				System.out.println();
			}
		}
		System.out.println("Transferências entre caixas concluída");

		System.out.println("Preparando mapas para o dia");
		MapasDoDia mdd = new MapasDoDia();
		mdd.prepararMapasDoDia(config.getPastaTemporaria());

		System.out.println("\n\nPersistindo dados de configuração");
		Persist.getInstance().salvarTudo();

		System.out.println("Fim da manipulação de Mapas Mentais");
		System.out.println("==================================================================");
	}

}
