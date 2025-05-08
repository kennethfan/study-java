package io.github.kennethfan.pdf;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

@Slf4j
public class PdfUtil {

    public static void main(String[] args) throws IOException {
        String html = "<html><body><h1>Hello PDF</h1><p>This is a test PDF generated from HTML</p></body></html>";
         html = Files.readString(Path.of("/Users/kenneth/Downloads/template-2.html"), StandardCharsets.UTF_8);
        String outputPdf = "output.pdf";

        try (OutputStream os = Files.newOutputStream(Paths.get(outputPdf))) {
            PdfRendererBuilder builder = new PdfRendererBuilder();

            builder.useFastMode();
            builder.withHtmlContent(html, null);
            builder.useFont(new File("simsun.ttc"), "宋体");
            builder.toStream(os);
            builder.run();
            log.info("PDF created successfully: {}", outputPdf);
        } catch (Exception e) {
            log.error("Failed to create PDF", e);
        }
    }
}
