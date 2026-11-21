package com.lulak.frugo.service.pdf;

import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfGeneratorService {

    public byte[] generatedPdf(String html){
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            PdfRendererBuilder builder = new PdfRendererBuilder();

            builder.withHtmlContent(html, null);

            builder.useFont(
                    () -> {
                        try {
                            return new ClassPathResource(
                                    "fonts/DejaVuSans.ttf"
                            ).getInputStream();
                        }catch (Exception e){
                            throw new RuntimeException(
                                    "Font loading failed", e
                            );
                        }
                    },
                    "DejaVu Sans",
                    400,
                    BaseRendererBuilder.FontStyle.NORMAL,
                    true
            );

            builder.toStream(outputStream);
            builder.run();

            return outputStream.toByteArray();
        }catch (Exception e){
            throw new RuntimeException(
                    "PDF generation failed", e
            );
        }
    }
}
