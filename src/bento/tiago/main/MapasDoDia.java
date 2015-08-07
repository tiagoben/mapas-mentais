package bento.tiago.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

public class MapasDoDia {
	public void prepararMapasDoDia(File saida) {
		ArrayList<File> pastasDeHoje = new ArrayList<File>();

		for (EnumCaixas caixa : EnumCaixas.values()) {
			boolean incluiuCaixa = false;
			for (Materia m : caixa.getListaMaterias()) {
				int qtdLeitura = m.getQtdLeitura();
				int diasDecorridos = (int) Data.diasDecorridos(m
						.getDataUltimaLeitura());

				if (caixa == EnumCaixas.DIARIA) {
					if(!incluiuCaixa){
						File pastaCaixa = new File(caixa.getPasta().getAbsolutePath());
						pastasDeHoje.add(pastaCaixa);
						incluiuCaixa = true;
					}
					File pasta = caixa.formarPastaMateria(m.getNome());
					pastasDeHoje.add(pasta);
					m.setDataUltimaLeitura(Calendar.getInstance());
				} else {
					if(diasDecorridos == 0){
						if(!incluiuCaixa){
							File pastaCaixa = new File(caixa.getPasta().getAbsolutePath());
							pastasDeHoje.add(pastaCaixa);
							incluiuCaixa = true;
						}
						
						File pasta = caixa.formarPastaMateria(m.getNome());
						pastasDeHoje.add(pasta);
					} else if (diasDecorridos >= caixa.getIntervaloDias()) {
						if(!incluiuCaixa){
							File pastaCaixa = new File(caixa.getPasta().getAbsolutePath());
							pastasDeHoje.add(pastaCaixa);
							incluiuCaixa = true;
						}
						
						File pasta = caixa.formarPastaMateria(m.getNome());
						pastasDeHoje.add(pasta);
						qtdLeitura++;
						m.setQtdLeitura(qtdLeitura);
						m.setDataUltimaLeitura(Calendar.getInstance());
					}
				}

			}
		}

		ManipuladorMapas mm = new ManipuladorMapas();
		mm.criarPDF(pastasDeHoje, saida);
		
//		abrirPasta(temp);
	}
	
}
