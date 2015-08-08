package bento.tiago.main;

import static bento.tiago.main.FileUtil.getPasta;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

public class Controle {

	final static Logger logger = Logger.getLogger(Controle.class);
	
	public void run() {
		informacoesIniciais();
		
		Config config = ConfigLoader.getConfig();
		
		if(config.isApenasUmArquivoSaida() && existeMapaNaPastaDeSaida()){
			logger.warn("Você não leu seu último mapa! Remova o arquivo e execute novamente.");	
		} else {
			Entrada.receberMapas();
			InicializadorCaixas.carregarMaterias();	
			ControleCaixas.executarTransferencias();
			List<File> pastasDeHoje = MapasDoDia.prepararMapasDoDia();	
			GeradorSaida.criarPDF(pastasDeHoje, getPasta(config.getPastaSaida()));
			Persist.getInstance().salvarTudo();
		}
		logger.info("Fim da manipulação de Mapas Mentais");
		logger.info("-----------------------------------------------------------------------\n");
	}

	private void informacoesIniciais(){
		logger.info("-----------------------------------------------------------------------");
		
		try {
		    InetAddress addr;
		    addr = InetAddress.getLocalHost();
		    String hostname = addr.getHostName();
		    logger.info(hostname);
		} catch(UnknownHostException ex) {
		   logger.error("O nome do host não pôde ser identificado", ex);
		}
		logger.info("Iniciando a manipulação de Mapas Mentais");
	}
	
	private boolean existeMapaNaPastaDeSaida(){
		Config config = ConfigLoader.getConfig();
		File pastaSaida = FileUtil.getPasta(config.getPastaSaida());
		
		List<String> arquivos = Arrays.asList(pastaSaida.list());
		
		boolean match = arquivos.stream()
			.anyMatch(arquivo -> 
				arquivo.startsWith(config.getPrefixoArquivoSaida()) 
				&& 
				arquivo.endsWith(".pdf")
			);
		
		return match;
	}

}
