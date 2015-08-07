package bento.tiago.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;



public class ControleCaixas {
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
					// TODO Auto-generated catch block
					// e.printStackTrace();
					System.err.println("A pasta \"" + m.getNome()
							+ "\" não pôde ser movida da caixa \""
							+ caixaOrigem.getNome() + "\" para caixa \""
							+ caixaDestino.getNome() + "\".");
					System.err.println(e);
				}
			}
		}

		return areaTransferencia;
	}
}
