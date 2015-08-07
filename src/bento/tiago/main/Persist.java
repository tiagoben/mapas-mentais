package bento.tiago.main;

import static bento.tiago.main.FileUtil.getArquivo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Persist {
	private static Persist persist;
	private ArrayList<String> catalogoPastas;

	private Persist() {

	}

	public static Persist getInstance() {
		if (persist == null) {
			persist = new Persist();
		}
		return persist;
	}

	public ArrayList<String> lerCatalogoPastas() {
		Config config = ConfigLoader.getConfig();
		
		if (catalogoPastas == null) {
			catalogoPastas = new ArrayList<String>();
		}
		catalogoPastas.clear();
		File arquivoOrdemPastas = getArquivo(config.getPastaMetadados(), config.getArquivoCatalogo());

		try {
			BufferedReader br = new BufferedReader(new FileReader(
					arquivoOrdemPastas));
			String linha = "";

			while ((linha = br.readLine()) != null) {
				linha = linha.trim();
				if (!linha.equals("")) {
					catalogoPastas.add(linha);
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return catalogoPastas;
	}

	public void salvarCatalogoPastas() {
		Config config = ConfigLoader.getConfig();
		
		if (catalogoPastas == null) {
			return;
		}

		File arquivoCatalogoPastas = getArquivo(config.getPastaMetadados(), config.getArquivoCatalogo());

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(
					arquivoCatalogoPastas));
			for (String s : catalogoPastas) {
				bw.write(s);
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> obterCatalogoPastas() {
		if (catalogoPastas == null) {
			return lerCatalogoPastas();
		} else {
			return catalogoPastas;
		}
	}

	public List<Materia> lerListaMaterias(EnumCaixas caixa) {
		Config config = ConfigLoader.getConfig();
		
		List<Materia> listaMaterias = new ArrayList<Materia>();

		File jsonMaterias = getArquivo(config.getPastaMetadados(), caixa.getArquivoMaterias());

		try {
			BufferedReader br = new BufferedReader(new FileReader(jsonMaterias));
			
			Materia[] array = new Gson().fromJson(br, Materia[].class);
			if(array != null){
				List<Materia> materiasLidas = new ArrayList<>(Arrays.asList(array));
				
				listaMaterias = materiasLidas.stream()
					.filter(m -> pastaMateriaExiste(caixa, m))
					.collect(Collectors.toList());
			}
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return listaMaterias;
	}

	private boolean pastaMateriaExiste(EnumCaixas caixa, Materia m) {
		String caminhoArquivo = EnumCaixas.getCaminhoCaixas()+"\\"+caixa.getNome()+"\\"+m.getNome();
		File arquivo = new File(caminhoArquivo);
		return arquivo.exists();
	}
	
	public void salvarListaMaterias(EnumCaixas caixa){
		Config config = ConfigLoader.getConfig();
		
		String caminho = config.getPastaMetadados() + "\\" + caixa.getArquivoMaterias();
		File arquivo = new File(caminho);
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo));
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(caixa.getListaMaterias());
			bw.write(json);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void salvarTudo() {
		salvarCatalogoPastas();
		for(EnumCaixas caixa: EnumCaixas.values()){
			salvarListaMaterias(caixa);
		}
	}
}
