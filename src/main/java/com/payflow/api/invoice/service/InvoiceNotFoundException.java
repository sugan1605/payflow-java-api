package com.payflow.api.invoice.service;

import java.util.UUID;

public class InvoiceNotFoundException extends RuntimeException {
    public InvoiceNotFoundException(UUID id) {
        super("Invoice with id " + id + " not found");
    }
}
