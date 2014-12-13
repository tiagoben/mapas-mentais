package bento.tiago.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

public class ManipuladorMapas {

	private static final int ALTURA_IMAGEM = 1080;
	private static final int LARGURA_IMAGEM = 1920;

	public void copiarTemporario(ArrayList<File> pastas, File temp) {
		limparPastaTemporaria(temp);
		
		ArrayList<File> arquivosDesenho = new ArrayList<File>();

		for (File p : pastas) {
			int num = p.listFiles().length;

			for (int i = 1; i <= num; i++) {
				String caminhoAbsoluto = p.getAbsolutePath() + "\\" + i
						+ ".png";
				File arquivo = new File(caminhoAbsoluto);
				if (arquivo.isFile()) {
					arquivosDesenho.add(arquivo);
				}
			}
		}

		for (int j = 1; j <= arquivosDesenho.size(); j++) {
			Path origem = arquivosDesenho.get(j - 1).toPath();
			String nomeArquivo = String.format("%04d", j);
			String caminhoDestino = temp.getAbsolutePath() + "\\" + nomeArquivo + ".png";
			Path destino = Paths.get(caminhoDestino);

			try {
				Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println(arquivosDesenho.size()+" mapas para hoje... :-)");
	}
	
	public void criarPDF(ArrayList<File> pastas, File temp) { 
		ArrayList<File> listaArquivosImagens = new ArrayList<File>();

		for (File p : pastas) {
			int num = p.listFiles().length;

			for (int i = 1; i <= num; i++) {
				String caminhoAbsoluto = p.getAbsolutePath() + "\\" + i
						+ ".png";
				File arquivo = new File(caminhoAbsoluto);
				if (arquivo.isFile()) {
					listaArquivosImagens.add(arquivo);
				}
			}
		}
		
		try{
			Document pdfDoc = new Document(new Rectangle(LARGURA_IMAGEM, ALTURA_IMAGEM), 0,0,0,0);
			LocalDate hoje = LocalDate.now();
			String nomeArquivo = hoje.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+".pdf";
			PdfWriter.getInstance(pdfDoc, new FileOutputStream(temp.getAbsolutePath() + "\\" + nomeArquivo));
			pdfDoc.open();
	
			for(File arquivoImagem: listaArquivosImagens){
				try {
					Image imagem = Image.getInstance(arquivoImagem.getAbsolutePath());
					float scaler = ((pdfDoc.getPageSize().getWidth() - pdfDoc.leftMargin()
							- pdfDoc.rightMargin()) / imagem.getWidth()) * 100;
					imagem.scalePercent(scaler);
					
					pdfDoc.add(imagem);
					pdfDoc.newPage();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
			}
			pdfDoc.add(new Paragraph("Fim :-D"));
			
			
			pdfDoc.close();
			System.out.println(listaArquivosImagens.size()+" mapas para hoje... :-)");
		} catch (Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	private void limparPastaTemporaria(File temp){
		if(temp.isDirectory()){
			for(File arquivoDeletar: temp.listFiles()){
				arquivoDeletar.delete();
			}
		}
	}

	public ArrayList<File> listarPastasCatalogadas(EnumCaixas caixa) {

		ArrayList<String> pastas = Persist.getInstance().obterCatalogoPastas();

		String caminho = caixa.getPasta().getAbsolutePath();

		ArrayList<File> listaPastas = new ArrayList<File>();

		for (String s : pastas) {
			File f = new File(caminho + "\\" + s);
			if (f.isDirectory()) {
				listaPastas.add(f);
			}
		}
		return listaPastas;
	}
	
	// public void moverPasta(File origem, File destino){
	// Path pathOrigem = origem.toPath();
	// Path pathDestino = destino.toPath();
	//
	// try {
	// Files.move(pathOrigem, pathDestino, StandardCopyOption.REPLACE_EXISTING);
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	
	public static void main(String[] args) throws MalformedURLException,  DocumentException, FileNotFoundException {
		LocalTime antes = LocalTime.now();

		Document pdfDoc = new Document(new Rectangle(1366, 768), 0,0,0,0);
		try {
			PdfWriter.getInstance(pdfDoc, new FileOutputStream("C:\\Users\\Tiago\\desktop\\arquivo.pdf"));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pdfDoc.open();
		File pasta = new File("C:\\Users\\Tiago\\Google Drive\\Concursos\\Mapas Mentais\\Mapinhas\\Mapas do dia");
		for(File f: pasta.listFiles()){
			
			Image imagem;
			try {
				imagem = Image.getInstance(f.getAbsolutePath());
				float scaler = ((pdfDoc.getPageSize().getWidth() - pdfDoc.leftMargin()
						- pdfDoc.rightMargin()) / imagem.getWidth()) * 100;
				imagem.scalePercent(scaler);
				
				pdfDoc.add(imagem);
				pdfDoc.newPage();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		LocalTime depois = LocalTime.now();
		
		Duration duration = Duration.between(antes, depois);
		
		pdfDoc.close();
		System.out.println("Done in " + duration.getSeconds()+ " seconds");
		
	}
}
