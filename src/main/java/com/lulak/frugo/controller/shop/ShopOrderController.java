package com.lulak.frugo.controller.shop;

import com.lulak.frugo.dto.OrderRequest;
import com.lulak.frugo.dto.PdfResult;
import com.lulak.frugo.service.PdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shop/orders")
@CrossOrigin("*")
public class ShopOrderController {

    private final PdfService pdfService;

    public ShopOrderController(PdfService pdfService){
        this.pdfService = pdfService;
    }

    @PostMapping("/pdf")
    public ResponseEntity<byte[]> generatePdf(@RequestBody OrderRequest request){
        PdfResult result = pdfService.generateInvoice(request);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                result.getFileName() +
                                "\""
                )
                .contentType(MediaType.APPLICATION_PDF)
                .body(result.getContent());
    }

}
