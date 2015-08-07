package bento.tiago.main;

import java.io.File;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class MapasDoDia {
	public void prepararMapasDoDia(File saida) {
		ArrayList<File> pastasDeHoje = new ArrayList<File>();

		for (EnumCaixas caixa : EnumCaixas.values()) {
			boolean incluiuCaixa = false;
			for (Materia m : caixa.getListaMaterias()) {
				int qtdLeitura = m.getQtdLeitura();
				int diasDecorridos = Period.between(m.getDataUltimaLeitura(), LocalDate.now()).getDays(); 

				if (caixa == EnumCaixas.DIARIA) {
					if(!incluiuCaixa){
						File pastaCaixa = new File(caixa.getPasta().getAbsolutePath());
						pastasDeHoje.add(pastaCaixa);
						incluiuCaixa = true;
					}
					File pasta = caixa.formarPastaMateria(m.getNome());
					pastasDeHoje.add(pasta);
					m.setDataUltimaLeitura(LocalDate.now());
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
						m.setDataUltimaLeitura(LocalDate.now());
					}
				}

			}
		}

		ManipuladorMapas mm = new ManipuladorMapas();
		mm.criarPDF(pastasDeHoje, saida);
		
//		abrirPasta(temp);
	}
	
}
