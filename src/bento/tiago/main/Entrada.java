package bento.tiago.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Entrada {
	
	public void receberMapas() throws IOException{
		Config config = ConfigLoader.getConfig();
		
		File pastaEntrada = FileUtil.getPasta(config.getPastaEntrada());
		File pastaOutrosArquivos = FileUtil.getPasta(config.getPastaOutrosArquivos());
		
		File catalogo = FileUtil.getArquivo(config.getPastaMetadados(), config.getArquivoCatalogo());
		BufferedWriter bw = new BufferedWriter(new FileWriter(catalogo, true));
		
		List<File> conteudoPastaEntrada = Arrays.asList(pastaEntrada.listFiles());
		
		conteudoPastaEntrada.stream()
			.filter(File::isDirectory)
			.sorted(Comparator.comparingLong(File::lastModified))
			.forEach((File materia) -> {
				adicionarComoMateria(materia, bw);
			});
		
		conteudoPastaEntrada.stream()
			.filter(File::isFile)
			.forEach((File arquivo) -> {
				moverOutroArquivo(arquivo, pastaOutrosArquivos);
			});
		
		bw.close();
	}
	
	private void adicionarComoMateria(File materia, BufferedWriter bw){
		try {
			File destino = new File(EnumCaixas.DIARIA.getPasta(), materia.getName());
			bw.newLine();
			bw.write(materia.getName());		
			Files.move(materia.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void moverOutroArquivo(File arquivo, File destino){
		destino = new File(destino, arquivo.getName());
		try {
			Files.move(arquivo.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
