package com.payflow.api.invoice;

import com.payflow.api.invoice.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Currency;

//tells spring how the class should be treated.
@RestController

// the base URL for the endpoints in this controller.
@RequestMapping("/api/invoices")

// this will handle invoice related HTTP requests.
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public Invoice createInvoice(@Valid @RequestBody CreateInvoiceRequest request) {


        Currency currency;

        try {
            currency = Currency.getInstance((request.currency()));
        } catch (IllegalArgumentException e) {
            throw new InvalidCurrencyCodeException(request.currency());
        }
        Invoice invoice = new Invoice(
                request.customerName(),
                request.amount(),
                currency
        );

        return invoiceService.save(invoice);
    }
}
