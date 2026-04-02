package com.lulak.frugo.controller;

import com.lulak.frugo.dto.OrderRequest;
import com.lulak.frugo.service.PdfService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("*")
public class OrderController {

    private final PdfService pdfService;

    public OrderController(PdfService pdfService){
        this.pdfService = pdfService;
    }

    @PostMapping("/pdf")
    public ResponseEntity<byte[]> generatePdf(@RequestBody OrderRequest request){
        byte[] pdf = pdfService.generateInvoice(request);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=faktura.pdf")
                .body(pdf);
    }

}
