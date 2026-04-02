package com.lulak.frugo.service;

import com.lulak.frugo.dto.OrderRequest;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;


@Service
public class PdfService {

    private final TemplateEngine templateEngine;

    public PdfService(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }

    public byte[] generateInvoice(OrderRequest order){
        try{
            Context context = new Context();

            context.setVariable("customer", order.customer);
            context.setVariable("items", order.items);
            context.setVariable("orderNumber", System.currentTimeMillis());
            context.setVariable("dateTime", LocalDateTime.now().toString());

            String html = templateEngine.process("invoice", context);

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(html, null);
            builder.toStream(out);
            builder.run();

            return out.toByteArray();
        } catch (Exception e){
            throw new RuntimeException("PDF generation failed", e);
        }
    }
}
