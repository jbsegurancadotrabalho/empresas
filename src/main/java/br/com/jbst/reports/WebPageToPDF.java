
package br.com.jbst.reports;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class WebPageToPDF {

    public byte[] convertWebPageToPDF(String url) throws IOException {
        // Crie uma instância de ByteArrayOutputStream para armazenar o PDF
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Crie um novo documento PDF
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(byteArrayOutputStream));

        // Crie um novo documento iText
        Document document = new Document(pdfDocument);

        // Defina as propriedades do conversor
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri(url);

        // Converta a página da web em PDF
        HtmlConverter.convertToPdf(url, pdfDocument, converterProperties);

        // Feche o documento iText
        document.close();

        // Retorne os bytes do PDF gerado
        return byteArrayOutputStream.toByteArray();
    }
}
