package bento.tiago.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

public class GeradorSaida {
	
	final static Logger logger = Logger.getLogger(GeradorSaida.class);

	private static final int ALTURA_IMAGEM = 1080;
	private static final int LARGURA_IMAGEM = 1920;
	
	public static void criarPDF(List<File> pastas, File saida) { 
		Config config = ConfigLoader.getConfig();
		ArrayList<File> listaArquivosImagens = new ArrayList<File>();

		for (File p : pastas) {
			int num = p.listFiles().length;

			for (int i = 1; i <= num; i++) {
				String caminhoAbsoluto = p.getAbsolutePath() + "\\" + i + ".png";
				File arquivo = new File(caminhoAbsoluto);
				if (arquivo.isFile()) {
					listaArquivosImagens.add(arquivo);
				}
			}
		}
		
		try{
			Document pdfDoc = new Document(new Rectangle(LARGURA_IMAGEM, ALTURA_IMAGEM), 0,0,0,0);
			LocalDate hoje = LocalDate.now();
			Font fonte = new Font(Font.FontFamily.HELVETICA, 50);
			String nomeArquivo = config.getPrefixoArquivoSaida()+hoje.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+".pdf";
			PdfWriter.getInstance(pdfDoc, new FileOutputStream(saida.getAbsolutePath() + "\\" + nomeArquivo));
			pdfDoc.open();
	
			
			
			for(File arquivoImagem: listaArquivosImagens){
				try {
					Image imagem = Image.getInstance(arquivoImagem.getAbsolutePath());
					float scaler = (
							(pdfDoc.getPageSize().getHeight() - pdfDoc.bottomMargin() - pdfDoc.topMargin()) 
							/ imagem.getHeight()) * 100;
					
					imagem.scalePercent(scaler);
					imagem.setAlignment(Element.ALIGN_CENTER);

					pdfDoc.add(imagem);
					pdfDoc.newPage();
				} catch (IOException e) {
					 e.printStackTrace();
				}
			}
			
			String txt = "Fim";
			
			Paragraph fim = new Paragraph(txt, fonte);
			pdfDoc.add(fim);
			
			pdfDoc.close();
			logger.info(listaArquivosImagens.size()+" mapas para hoje");
		} catch (Exception e){
			logger.error("Ocorreu um erro ao gerar o pdf", e);
		}
			
	}
		
}
