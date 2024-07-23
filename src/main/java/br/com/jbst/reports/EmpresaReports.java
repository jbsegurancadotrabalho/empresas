package br.com.jbst.reports;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.jbst.entities.Empresa;
import br.com.jbst.entities.Funcionario;


@Service
public class EmpresaReports {

    
  
	public ByteArrayInputStream createPdf(List<Empresa> empresa) throws Exception  {
		
		
		
		// Criando um documento PDF com o iText
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Document document = new Document(PageSize.A4, 40, 40, 40, 40); // Margens de 50 unidades
		PdfWriter pdfWriter = PdfWriter.getInstance(document, out);
	

		// Abrindo o arquivo PDF
		document.open();
		// Título do relatório com formatação de texto
		Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
		Paragraph title = new Paragraph("Relatório do Funcionário da Empresa e suas Funções", titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		document.add(title);
		// Espaçamento após o título
		document.add(new Paragraph("\n"));
		// Desenhando uma tabela para exibir os funcionarios
		PdfPTable table = new PdfPTable(5); // Adicionei uma coluna para a imagem
		table.setWidthPercentage(100); // Largura 100%
			
		// Títulos das colunas com formatação de texto
		Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
		PdfPCell cell = new PdfPCell(new Phrase("Nome da Empresa", headerFont));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("Funcionario", headerFont));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("CPF", headerFont));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Função", headerFont));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("assinatura", headerFont));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
			
		
		// Conteúdo da tabela com formatação de texto - Empresa	
		Font contentFont = new Font(Font.FontFamily.HELVETICA, 10);	
     	for (Empresa empresas : empresa) {
		cell = new PdfPCell(new Phrase(empresas.getRazaosocial(), contentFont));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);
		
		// Conteúdo da tabela com formatação de texto - Funcionário	
		String funcionarios = "";
		for (Funcionario funcionario : empresas.getFuncionarios()) {	
		funcionarios += funcionario.getNome() + ", ";
		cell = new PdfPCell(new Phrase(funcionario.getNome().toString(), contentFont));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase(funcionario.getCpf().toString(), contentFont));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(cell);	
		
		// Adicionando a imagem do produto em tamanho reduzido
		byte[] imagemFuncionario = funcionario.getAssinatura();
		if (imagemFuncionario != null && imagemFuncionario.length > 0) {
		Image img = Image.getInstance(imagemFuncionario);
		img.scaleToFit(40, 40); // Tamanho máximo de 80x80 pixels
		PdfPCell imgCell = new PdfPCell(img, true);
		table.addCell(imgCell);	
		
		
		} else {
		table.addCell(""); // Se não houver imagem, insira uma célula vazia
		
		                     
		           }
		       }
     	}
		
		// Adicionando a tabela no PDF
		document.add(table);
		// Fechando o documento PDF
		document.close();
		pdfWriter.close();
		// Retornando os dados do arquivo em bytes
		return new ByteArrayInputStream(out.toByteArray());
		
}
}
	

	
