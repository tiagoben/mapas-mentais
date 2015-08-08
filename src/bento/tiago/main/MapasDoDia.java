package bento.tiago.main;

import java.io.File;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class MapasDoDia {
	public static List<File> prepararMapasDoDia() {
		ArrayList<File> pastasDeHoje = new ArrayList<File>();

		for (EnumCaixas caixa : EnumCaixas.values()) {
			boolean incluiuCaixa = false;
			for (Materia m : caixa.getListaMaterias()) {
				int qtdLeitura = m.getQtdLeitura();
				int diasDecorridos = Period.between(m.getDataUltimaLeitura(), LocalDate.now()).getDays(); 
				
				if(diasDecorridos == 0){
					if(!incluiuCaixa){
						incluirCaixa(pastasDeHoje, caixa);
						incluiuCaixa = true;
					}
					
					incluirMateria(pastasDeHoje, caixa, m);
				} else if (diasDecorridos >= caixa.getIntervaloDias()) {
					if(!incluiuCaixa){
						incluirCaixa(pastasDeHoje, caixa);
						incluiuCaixa = true;
					}					
					incluirMateria(pastasDeHoje, caixa, m);
					
					qtdLeitura++;
					m.setQtdLeitura(qtdLeitura);
					m.setDataUltimaLeitura(LocalDate.now());
				}
			}
		}

		return pastasDeHoje;
		
	}

	private static void incluirMateria(ArrayList<File> pastasDeHoje, EnumCaixas caixa, Materia m) {
		File materia = caixa.formarPastaMateria(m.getNome());
		pastasDeHoje.add(materia);
	}

	private static void incluirCaixa(ArrayList<File> pastasDeHoje, EnumCaixas caixa) {
		File pastaCaixa = new File(caixa.getPasta().getAbsolutePath());
		pastasDeHoje.add(pastaCaixa);
	}
	
}
