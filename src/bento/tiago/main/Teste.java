package bento.tiago.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Teste {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
//		File f = new File("c:\\java.txt");
//		Calendar hoje = Calendar.getInstance();
//		hoje.set(Calendar.HOUR_OF_DAY, 0);
//		hoje.set(Calendar.MINUTE, 0);
//		hoje.set(Calendar.SECOND, 0);
//		hoje.set(Calendar.MILLISECOND, 0);
//		
//		Calendar diaArq = Calendar.getInstance();	
//		diaArq.setTimeInMillis(f.lastModified());
//		diaArq.set(Calendar.HOUR_OF_DAY, 0);
//		diaArq.set(Calendar.MINUTE, 0);
//		diaArq.set(Calendar.SECOND, 0);
//		diaArq.set(Calendar.MILLISECOND, 0);
//		
//		
//		String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
//		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
//
//		System.out.println("Arq: "+sdf.format(diaArq.getTime()));
//		System.out.println("Hoje: "+sdf.format(hoje.getTime()));
//		
//		long dif = hoje.getTimeInMillis()-diaArq.getTimeInMillis();
//		
//		long dias = ((((dif)/1000)/60)/60)/24;
//		
//		System.out.println(dias);
		
		File pasta1 = new File("D:\\testes\\mapasmentais\\RUP - Início");
		File pasta2 = new File("D:\\testes\\mapasmentais\\RUP-Conceitos Básicos");
		
		ArrayList<File> listaArquivos = new ArrayList<File>();
		
		File[] fis1 = pasta1.listFiles();
		
		for(File f: fis1){
			listaArquivos.add(f);
		}
		
		File[] fis2 = pasta2.listFiles();
		
		for(File f: fis2){
			listaArquivos.add(f);
		}
		
		
		for(int i=0;i<listaArquivos.size();i++){
			Path origem = listaArquivos.get(i).toPath();
			Path destino = Paths.get("D:\\testes\\mapasmentais\\aaa\\"+(i+1)+".png");
			
			Files.copy(origem, destino);
			
			System.out.println(destino);
			
		}
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
