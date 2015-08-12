package bento.tiago.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

public class Log4jUtil {
	
	public void setLogFileInCurrentFolder(){
		String logFile = FileUtil.getPasta("./").getAbsolutePath() + "/mapas.log";
		updateLog4jConfiguration(logFile);
	}
	
	public void updateLog4jConfiguration(String logFile) { 
	    Properties props = new Properties(); 
	    try { 
	        InputStream configStream = getClass().getResourceAsStream( "/log4j.properties"); 
	        props.load(configStream); 
	        configStream.close(); 
	    } catch (IOException e) { 
	        System.out.println("Erro ao ler arquivo de propriedades ");
	        e.printStackTrace();
	    } 
	    props.setProperty("log4j.appender.file.File", logFile); 
	    LogManager.resetConfiguration(); 
	    PropertyConfigurator.configure(props); 
	 }
}
