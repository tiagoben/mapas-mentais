package bento.tiago.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;



public class ControleCaixas {
	
	final static Logger logger = Logger.getLogger(ControleCaixas.class);
	
	public static void executarTransferencias() {
		boolean houveTransferencias = false;
		
		for (int i = 0; i < EnumCaixas.values().length - 1; i++) {
			EnumCaixas caixaOrigem = EnumCaixas.values()[i];
			EnumCaixas caixaDestino = EnumCaixas.values()[i + 1];
			int maxLeituras = caixaOrigem.getMaxLeituras();
			ArrayList<Materia> transferidos = ControleCaixas.transferirMaterias(caixaOrigem, caixaDestino, maxLeituras,true);

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

				logger.info(countMapas + " mapas em "
						+ nomesPastas.size() + " pastas transferidos de "
						+ caixaOrigem.getNome() + " para "
						+ caixaDestino.getNome());

				for (String s : nomesPastas) {
					logger.info("\t" + s);
				}
				
				houveTransferencias = true;
			} 
		}
		
		if(!houveTransferencias){
			logger.info("Não houve transferência de matérias entre caixas");
		}
	}
	
	public static ArrayList<Materia> transferirMaterias(
			EnumCaixas caixaOrigem, EnumCaixas caixaDestino, int maxLeitura,
			boolean commit) {
		ArrayList<Materia> areaTransferencia = new ArrayList<Materia>();

		List<Materia> materiasOrigem = caixaOrigem.getListaMaterias();

		for (Materia m : materiasOrigem) {
			if (m.getQtdLeitura() >= maxLeitura) {
				areaTransferencia.add(m);
			}
		}

		if (commit) {
			for (Materia m : areaTransferencia) {
				m.setQtdLeitura(0);
				caixaOrigem.removeMateria(m);
				caixaDestino.addMateria(m);

				Path pastaOrigem = caixaOrigem.formarPastaMateria(m.getNome())
						.toPath();
				Path pastaDestino = caixaDestino
						.formarPastaMateria(m.getNome()).toPath();

				try {
					Files.move(pastaOrigem, pastaDestino,
							StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					String msg = "A pasta \"" + m.getNome()
					+ "\" não pôde ser movida da caixa \""
					+ caixaOrigem.getNome() + "\" para caixa \""
					+ caixaDestino.getNome() + "\"";

					logger.error(msg, e);
				}
			}
		}
		return areaTransferencia;
	}
}
