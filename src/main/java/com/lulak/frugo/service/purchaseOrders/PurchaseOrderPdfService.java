package com.lulak.frugo.service.purchaseOrders;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lulak.frugo.dto.purchaseOrders.list.AdminPurchaseOrderDetailDto;
import com.lulak.frugo.dto.purchaseOrders.list.AdminPurchaseOrderItemDto;
import com.lulak.frugo.service.pdf.PdfGeneratorService;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
public class PurchaseOrderPdfService {

    private final AdminPurchaseOrderService purchaseOrderService;
    private final PdfGeneratorService pdfGeneratorService;
    private final TemplateEngine templateEngine;

    public PurchaseOrderPdfService(
            AdminPurchaseOrderService purchaseOrderService,
            PdfGeneratorService pdfGeneratorService,
            TemplateEngine templateEngine
    ){
        this.purchaseOrderService = purchaseOrderService;
        this.pdfGeneratorService = pdfGeneratorService;
        this.templateEngine = templateEngine;
    }

    public byte[] generatePurchaseOrderPdf(Integer purchaseOrderId){
        AdminPurchaseOrderDetailDto purchaseOrder =
                purchaseOrderService.getPurchaseOrderDetail(purchaseOrderId);

        String qrCode = generateQrCode(
                purchaseOrder.getPurchaseOrderNumber()
        );

        int totalQuantity = 0;
        int totalReceivedQuantity = 0;

        for(AdminPurchaseOrderItemDto item : purchaseOrder.getItems()){
            totalQuantity += item.getQuantity();
            totalReceivedQuantity += item.getReceivedQuantity();
        }

        int totalRemainingQuantity = totalQuantity - totalReceivedQuantity;

        Context context = new Context();

        context.setVariable("purchaseOrder", purchaseOrder);
        context.setVariable("qrCode", qrCode);

        context.setVariable("totalQuantity", totalQuantity);
        context.setVariable("totalReceivedQuantity", totalReceivedQuantity);
        context.setVariable("totalRemainingQuantity", totalRemainingQuantity);

        context.setVariable("createdAt", purchaseOrder.getCreatedAt().format(
                DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
        ));

        String html = templateEngine.process("purchase-order", context);

        return pdfGeneratorService.generatedPdf(html);
    }

    private String generateQrCode(String value){
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();

            BitMatrix bitMatrix = qrCodeWriter.encode(
                    value,
                    BarcodeFormat.QR_CODE,
                    250,
                    250
            );

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            MatrixToImageWriter.writeToStream(
                    bitMatrix,
                    "PNG",
                    outputStream
            );

            String base64 = Base64.getEncoder()
                    .encodeToString(outputStream.toByteArray());

            return "data:image/png;base64," + base64;
        }catch (Exception e){
            throw new RuntimeException(
                    "QR code generation failed",e
            );
        }
    }
}
