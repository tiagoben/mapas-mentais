package bento.tiago.main;

public class Logger {
	private static String log = "";
	
	public static void info(Object linha){
		System.out.println(linha);
		log = log + linha.toString() + "\n";
	}
	
	public static String getLog(){
		return log;
	}
}
