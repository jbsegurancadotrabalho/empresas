package br.com.jbst.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jbst.reports.WebPageToPDF;

import java.io.IOException;

@RestController
@RequestMapping("/gerar-pdf")
public class PDFController {

    @Autowired
    private WebPageToPDF webPageToPDF;

    @GetMapping
    public ResponseEntity<byte[]> gerarPDF() {
        try {
            // Substitua a URL abaixo pela URL da página que você deseja converter em PDF
            String urlDaPagina = "https://jbsegurancadotrabalho.com.br";

            // Converta a página da web em PDF
            byte[] pdfBytes = webPageToPDF.convertWebPageToPDF(urlDaPagina);

            // Configure os cabeçalhos de resposta
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "jbsegurancadotrabalho.pdf");

            // Retorne o PDF gerado como uma resposta HTTP
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
