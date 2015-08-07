package bento.tiago.main;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class FileUtil {
	
	public static File getPasta(String pasta){
		File pastaFile = new File(pasta);
		if(!pastaFile.isDirectory()){
			pastaFile.mkdirs();
		}
		return pastaFile;
	}
	
	public static File getArquivo(String pasta, String nomeArquivo){
		File arquivo = new File(pasta+"/"+nomeArquivo);	
		File parent = arquivo.getParentFile();
		
		if(!parent.exists()){
			parent.mkdirs();
		}
		
		if(!arquivo.exists()){
			try {
				arquivo.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return arquivo;
	}
	
	public static LocalDate getDataModificacao(File arquivo){
		Instant instant = Instant.ofEpochMilli(arquivo.lastModified());
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		LocalDate localDate = localDateTime.toLocalDate();
		return localDate;
	}

}
