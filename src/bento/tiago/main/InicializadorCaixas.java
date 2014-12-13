package bento.tiago.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

public class InicializadorCaixas {
	public static void init(EnumCaixas caixa){
		if(caixa==EnumCaixas.DIARIA){
			initDiaria(caixa);
		} else {
			initOutras(caixa);
		}
	}
	
	private static void initDiaria(EnumCaixas caixa){
		ArrayList<Materia> materias = caixa.getListaMaterias();
		materias.clear();
		
		ManipuladorMapas ma = new ManipuladorMapas();		
		ArrayList<File> pastasCaixaDiaria = ma.listarPastasCatalogadas(EnumCaixas.DIARIA);
		
		for(File f: pastasCaixaDiaria){
			Materia materia = new Materia();
			materia.setNome(f.getName());
			
			File referencia = f;
			File[] arquivos = f.listFiles();
			for(File arq: arquivos){
				if(arq.lastModified()<f.lastModified()){
					referencia = arq;
				}
			}

			int diasDecorridos = Data.diasUltimaMod(referencia);
			materia.setQtdLeitura(diasDecorridos);
			
			Calendar ontem = Calendar.getInstance();
			ontem.add(Calendar.DATE, -1);
			materia.setDataUltimaLeitura(ontem);
			
			caixa.addMateria(materia);
		}		
	}
	
	private static void initOutras(EnumCaixas caixa){
		caixa.getListaMaterias().clear();
		
		Persist persist = Persist.getInstance();
		ArrayList<Materia> materias = persist.lerListaMaterias(caixa);
		
		for(Materia m: materias){
			caixa.addMateria(m);
		}
		
	}
	
}
