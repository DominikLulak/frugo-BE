package com.lulak.frugo.service;

import com.lulak.frugo.dto.OrderItemDto;
import com.lulak.frugo.dto.OrderRequest;
import com.lulak.frugo.dto.PdfResult;
import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
public class PdfService {

    private final TemplateEngine templateEngine;
    private final TranslationService translationService;

    public PdfService(TemplateEngine templateEngine, TranslationService translationService){
        this.templateEngine = templateEngine;
        this.translationService = translationService;
    }

    public PdfResult generateInvoice(OrderRequest order){
        try{
            for(OrderItemDto item : order.items){
                item.name = translationService.translateProduct(item.name);
                item.category = translationService.translateCategory(item.category);
            }

            String orderNumber = "FRG-" + System.currentTimeMillis();

            Context context = new Context();

            context.setVariable("customer", order.customer);
            context.setVariable("items", order.items);
            context.setVariable("orderNumber", orderNumber);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss");

            context.setVariable("dateTime", LocalDateTime.now().format(formatter));

            String html = templateEngine.process("invoice", context);

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(html, null);

            builder.useFont(
                    () -> {
                        try {
                            return new ClassPathResource("fonts/DejaVuSans.ttf").getInputStream();
                        }catch(Exception e){
                            throw new RuntimeException(e);
                        }
                    },
                    "DejaVu Sans",
                    400,
                    BaseRendererBuilder.FontStyle.NORMAL,
                    true
            );

            builder.toStream(out);
            builder.run();

            return new PdfResult(
                    orderNumber + ".pdf",
                    out.toByteArray()
            );

        } catch (Exception e){
            throw new RuntimeException("PDF generation failed", e);
        }
    }
}
