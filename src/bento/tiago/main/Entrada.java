package bento.tiago.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

public class Entrada {
	
	final static Logger logger = Logger.getLogger(Entrada.class);
	
	public static void receberMapas(){
		Config config = ConfigLoader.getConfig();
		
		File pastaEntrada = FileUtil.getPasta(config.getPastaEntrada());
		File pastaOutrosArquivos = FileUtil.getPasta(config.getPastaOutrosArquivos());
		
		File catalogo = FileUtil.getArquivo(config.getPastaMetadados(), config.getArquivoCatalogo());
		BufferedWriter bw;
		
		List<String> nomesMateriasAdicionadas = new ArrayList<>();
		
		try {
			bw = new BufferedWriter(new FileWriter(catalogo, true));
			List<File> conteudoPastaEntrada = Arrays.asList(pastaEntrada.listFiles());
			
			
			conteudoPastaEntrada.stream()
				.filter(File::isDirectory)
				.sorted((f1, f2) -> { return (int)(f1.listFiles()[0].lastModified()-f2.listFiles()[0].lastModified());})
				.forEach((File materia) -> {
					adicionarComoMateria(materia, bw);
					nomesMateriasAdicionadas.add(materia.getName());
				});
			
			conteudoPastaEntrada.stream()
				.filter(File::isFile)
				.forEach((File arquivo) -> {
					moverOutroArquivo(arquivo, pastaOutrosArquivos);
				});
			
			bw.close();
		} catch (IOException e) {
			logger.error("Ocorreu um erro ao receber mapas da caixa de entrada", e);
		}
		
		if(nomesMateriasAdicionadas.isEmpty()){
			logger.info("Nenhuma nova matéria na caixa de entrada");
		} else {
			int qtd = nomesMateriasAdicionadas.size();
			logger.info(qtd + " nova(s) matéria(s): " + nomesMateriasAdicionadas);
		}
		
	}
	
	private static void adicionarComoMateria(File materia, BufferedWriter bw){
		try {
			File destino = new File(EnumCaixas.DIARIA.getPasta(), materia.getName());
			bw.newLine();
			bw.write(materia.getName());		
			Files.move(materia.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			logger.error("Ocorreu um erro ao transferir pastas com matérias para caixa diária", e);
		}
	}
	
	private static void moverOutroArquivo(File arquivo, File destino){
		destino = new File(destino, arquivo.getName());
		try {
			Files.move(arquivo.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			logger.error("Ocorreu um erro ao outros tipos de arquivos", e);
		}
	}
	
	
}
