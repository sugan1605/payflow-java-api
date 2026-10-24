package com.payflow.api.invoice.persistence;

import com.payflow.api.invoice.Invoice;

import org.springframework.stereotype.Component;

@Component
public class InvoiceMapper {

    public InvoiceEntity toEntity(Invoice invoice) {
        return new InvoiceEntity(
                invoice.getId(),
                invoice.getCustomerName(),
                invoice.getAmount(),
                invoice.getCurrency(),
                invoice.getStatus()
        );
    }

    public Invoice toDomain(InvoiceEntity entity) {
        return new Invoice(
                entity.getId(),
                entity.getCustomerName(),
                entity.getAmount(),
                entity.getCurrency(),
                entity.getStatus()

        );
    }
}
