package bento.tiago.main;

import java.io.File;
import java.io.IOException;

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

}
