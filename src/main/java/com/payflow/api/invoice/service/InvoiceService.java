package com.payflow.api.invoice.service;

import com.payflow.api.invoice.Invoice;
import com.payflow.api.invoice.persistence.InvoiceEntity;
import com.payflow.api.invoice.persistence.InvoiceRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;


@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public List<InvoiceEntity> findAll() {
        return invoiceRepository.findAll();
    }

    public InvoiceEntity findById(UUID id) {
        return invoiceRepository.findById(id).orElseThrow(() -> new InvoiceNotFoundException(id));
    }
}
