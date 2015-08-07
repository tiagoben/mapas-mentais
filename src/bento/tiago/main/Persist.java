package bento.tiago.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;
import static bento.tiago.main.FileUtil.*;

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

	public ArrayList<Materia> lerListaMaterias(EnumCaixas caixa) {
		Config config = ConfigLoader.getConfig();
		
		ArrayList<Materia> listaMaterias = new ArrayList<Materia>();

		File arquivo = getArquivo(config.getPastaMetadados(), caixa.getArquivoMaterias());

		try {
			BufferedReader br = new BufferedReader(new FileReader(arquivo));
			String linha = "";

			while ((linha = br.readLine()) != null) {
				linha = linha.trim();
				if (!linha.equals("")) {
					StringTokenizer tokenizer = new StringTokenizer(linha, "\t");
					String nome = tokenizer.nextToken();

					Calendar data = Calendar.getInstance();
					Data.zeroHora(data);
					try {
						data = Data.getCalendar(tokenizer.nextToken());
					} catch (Exception ex) {
						ex.printStackTrace();
					}

					int leituras = 0;
					try {
						leituras = Integer.parseInt(tokenizer.nextToken());
					} catch (Exception ex) {

					}
					
					Materia m = new Materia();
					m.setNome(nome);
					m.setDataUltimaLeitura(data);
					m.setQtdLeitura(leituras);
					
					String caminhoTeste = EnumCaixas.getCaminhoCaixas()+"\\"+caixa.getNome()+"\\"+m.getNome();
					File arquivoTeste = new File(caminhoTeste);
					if(arquivoTeste.exists()){
						listaMaterias.add(m);
					}

				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return listaMaterias;
	}
	
	public void salvarListaMaterias(EnumCaixas caixa){
		Config config = ConfigLoader.getConfig();
		
		String caminho = config.getPastaMetadados() + "\\"
		+ caixa.getArquivoMaterias();
		File arquivo = new File(caminho);
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo));
			for(Materia m: caixa.getListaMaterias()){
				String data = Data.getDataFormatada(m.getDataUltimaLeitura());
				
				bw.write(m.getNome());
				bw.write("\t");
				bw.write(data);
				bw.write("\t");
				bw.write(""+m.getQtdLeitura());
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
