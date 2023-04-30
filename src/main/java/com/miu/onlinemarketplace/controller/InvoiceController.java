package com.miu.onlinemarketplace.controller;

import com.miu.onlinemarketplace.common.dto.InvoiceDto;
import com.miu.onlinemarketplace.service.invoice.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getInvoice(@PathVariable("orderId") Long orderId) {
        InvoiceDto invoiceDto = invoiceService.generateInvoiceByOrderId(orderId);
        if(invoiceDto != null) {
            return new ResponseEntity<>(invoiceDto, HttpStatus.OK);
        }
        return new ResponseEntity<>("Invoice Generation Failed!!!", HttpStatus.BAD_REQUEST);
    }
}
