package io.github.kennethfan.pdf;

import lombok.extern.slf4j.Slf4j;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class PdfUtil {

    public static void main(String[] args) {
        String html = "<html><body><h1>Hello PDF</h1><p>This is a test PDF generated from HTML</p></body></html>";
        String outputPdf = "output.pdf";

        try (OutputStream os = Files.newOutputStream(Paths.get(outputPdf))) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(os);
            log.info("PDF created successfully: {}", outputPdf);
        } catch (Exception e) {
            log.error("Failed to create PDF", e);
        }
    }
}
