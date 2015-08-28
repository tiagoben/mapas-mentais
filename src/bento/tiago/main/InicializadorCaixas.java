package bento.tiago.main;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class InicializadorCaixas {
	public static void carregarMaterias(){
		List<String> catalogo = Persist.getInstance().obterCatalogoPastas();

		for (EnumCaixas caixa : EnumCaixas.values()) {
			caixa.getListaMaterias().clear();
			
			Persist persist = Persist.getInstance();
			List<Materia> materias = persist.lerListaMaterias(caixa);
			
			List<String> nomesMateriais = materias.stream()
					.map(Materia::getNome)
					.collect(Collectors.toList());
			
			
			catalogo.stream()
				.filter(item -> !nomesMateriais.contains(item))
				.filter(item -> new File(caixa.getPasta(), item).isDirectory())
				.forEach(item ->{
					Materia novaMateria = new Materia();
					novaMateria.setNome(item);
					novaMateria.setQtdLeitura(1);
					novaMateria.setDataUltimaLeitura(LocalDate.now().minusDays(1));
					materias.add(novaMateria);
				});
			
			materias.sort( (m1, m2) -> catalogo.indexOf(m2.getNome()) - catalogo.indexOf(m1.getNome()) );
			materias.forEach(m -> caixa.addMateria(m));
		}
	}
}
