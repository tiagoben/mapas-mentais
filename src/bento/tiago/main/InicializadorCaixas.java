package bento.tiago.main;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InicializadorCaixas {
	public static void init(EnumCaixas caixa){
		caixa.getListaMaterias().clear();
		
		Persist persist = Persist.getInstance();
		List<Materia> materias = persist.lerListaMaterias(caixa);
		
		List<String> nomesMateriais = materias.stream()
				.map(Materia::getNome)
				.collect(Collectors.toList());
		
		ArrayList<String> catalogo = Persist.getInstance().obterCatalogoPastas();
		
		catalogo.stream()
			.filter(item -> !nomesMateriais.contains(item))
			.filter(item -> new File(caixa.getPasta()+"/"+item).isDirectory())
			.forEach(item ->{
				Materia novaMateria = new Materia();
				novaMateria.setNome(item);
				novaMateria.setQtdLeitura(0);
				novaMateria.setDataUltimaLeitura(LocalDate.now().minusDays(1));
				materias.add(novaMateria);
			});
		materias.forEach(m -> caixa.addMateria(m));
	}
}
