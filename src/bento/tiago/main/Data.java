package bento.tiago.main;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;

public class Data {
	public static void zeroHora(Calendar cal){
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
	}
	
	public static long diasDecorridos(Calendar referencia){
		long dias = 0;
		
		Calendar anterior = Calendar.getInstance();
		anterior.setTimeInMillis(referencia.getTimeInMillis());
		zeroHora(anterior);
		
		Calendar hoje = Calendar.getInstance();
		zeroHora(hoje);
		
		long dif = hoje.getTimeInMillis()-anterior.getTimeInMillis();
		dias = ((((dif)/1000)/60)/60)/24;
		
		return dias;
	}
	
	public static int diasUltimaMod(File arquivo){
		Calendar dataModificacao = Calendar.getInstance();
		dataModificacao.setTimeInMillis(arquivo.lastModified());
		int diasDecorridos = (int)Data.diasDecorridos(dataModificacao);
		return diasDecorridos;
	}
	
	public static String getDataFormatada(Calendar cal){
		String DATE_FORMAT = "dd/MM/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.format(cal.getTime());
	}
	
	public static Calendar getCalendar(String dataFormatada){
		StringTokenizer tokenizer = new StringTokenizer(dataFormatada,"/");
		
		int dia = Integer.parseInt(tokenizer.nextToken());
		int mes = Integer.parseInt(tokenizer.nextToken())-1;
		int ano = Integer.parseInt(tokenizer.nextToken());
		Calendar cal = Calendar.getInstance();
		cal.set(ano, mes, dia, 0, 0, 0);

		return cal;
	}
}
