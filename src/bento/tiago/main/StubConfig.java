package bento.tiago.main;

import java.io.File;

public class StubConfig {
	
	private static int maxLeituraDiaria = 20;
	private static int maxLeituraSemanal = 6;
	
	private static String raiz = "C:\\Users\\Tiago\\Google Drive\\Mapas Mentais\\Caixas";
//	private static String raiz = "C:\\Users\\Tiago\\Google Drive\\Concursos\\Mapas Mentais\\Mapinhas";
//	private static String raiz = "D:\\testes\\mapasmentais";
	
	private static File pastaConfig = new File(raiz+"\\config");
	private static File arquivoCatalogoPastas = new File(raiz+"\\catalogoPastas.txt");
	private static File pastaTemporaria = new File("C:\\Users\\Tiago\\Google Drive\\Mapas Mentais\\Mapas do dia");
//	private static File pastaTemporaria = new File(raiz+"\\Mapas do dia");
//	private static File pastaTemporaria =  new File("D:\\testes\\mapasmentais\\temp");;	
	private static String caminhoCaixas = raiz;
	
	private static Config c;
	
	public static Config getConfig(){
		if(c==null){
			c = new Config();
		
			c.setArquivoCatalogoPastas(arquivoCatalogoPastas);
			c.setPastaTemporaria(pastaTemporaria);
			c.setPastaConfig(pastaConfig);
		
			EnumCaixas.setCaminhoCaixas(caminhoCaixas);
			
			Persist persist = Persist.getInstance();
			persist.setConfig(c);
			
			EnumCaixas.DIARIA.setMaxLeituras(maxLeituraDiaria);
			EnumCaixas.SEMANAL.setMaxLeituras(maxLeituraSemanal);
			
		}
		return c;
	}
}
