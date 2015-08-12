package bento.tiago.main;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.apache.log4j.Logger;

public class FileUtil {
	
	final static Logger logger = Logger.getLogger(FileUtil.class);
	
	public static File getPasta(String pasta){
		File pastaFile;
		if(pasta.startsWith(".")){
			pastaFile = new File(getCurrentFolder(), pasta);
		}else{
			pastaFile = new File(pasta);
		}
		if(!pastaFile.isDirectory()){
			pastaFile.mkdirs();
		}
		return pastaFile;
	}
	
	public static File getArquivo(String pasta, String nomeArquivo){
		File pastaFile = getPasta(pasta);
		File arquivo = new File(pastaFile, nomeArquivo);	
		
		if(!arquivo.exists()){
			try {
				arquivo.createNewFile();
			} catch (IOException e) {
				logger.error("Erro ao criar arquivo "+arquivo.getAbsolutePath(), e);
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
	
	public static File getCurrentFolder(){
		String path = "";
		try {
			path = URLDecoder.decode(ClassLoader.getSystemClassLoader().getResource(".").getPath(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("Erro ao obter pasta atual ", e);
		}
		File folder = new File(path);
		return folder;
	}

}
