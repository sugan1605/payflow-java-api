package com.payflow.api.invoice.service;

import com.payflow.api.invoice.Invoice;
import com.payflow.api.invoice.persistence.InvoiceEntity;
import com.payflow.api.invoice.persistence.InvoiceMapper;
import com.payflow.api.invoice.persistence.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;

    public InvoiceService(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
    }

    public List<Invoice> findAll() {
        return invoiceRepository.findAll()
                .stream().map(invoiceMapper::toDomain).toList();
    }

    public Invoice findById(UUID id) {
        InvoiceEntity entity = invoiceRepository.findById(id)
                .orElseThrow(() -> new InvoiceNotFoundException(id));
        return invoiceMapper.toDomain(entity);
    }

    public Invoice save(Invoice invoice) {
        InvoiceEntity entity = invoiceMapper.toEntity(invoice);

        InvoiceEntity savedEntity = invoiceRepository.save(entity);

        return invoiceMapper.toDomain(savedEntity);
    }
}
