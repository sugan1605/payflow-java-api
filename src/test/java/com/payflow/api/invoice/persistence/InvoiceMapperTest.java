package com.payflow.api.invoice.persistence;

import com.payflow.api.invoice.Invoice;
import com.payflow.api.invoice.InvoiceStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvoiceMapperTest {
    private final InvoiceMapper invoiceMapper = new InvoiceMapper();

    @Test
    void toEntity_preservesInvoiceData() {
        Invoice invoice = new Invoice(
                "test firma",
                new BigDecimal("1250.50"),
                Currency.getInstance("NOK")
        );

        InvoiceEntity entity = invoiceMapper.toEntity(invoice);

        assertEquals(invoice.getId(), entity.getId());
        assertEquals(invoice.getCustomerName(), entity.getCustomerName());
        assertEquals(invoice.getAmount(), entity.getAmount());
        assertEquals(invoice.getCurrency(), entity.getCurrency());
        assertEquals(invoice.getStatus(), entity.getStatus());

    }

    @Test
    void toDomain_preservesExistingInvoiceData() {
        UUID id = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("250.50");

        InvoiceEntity entity = new InvoiceEntity(
                id,
                "test AS",
                amount,
                Currency.getInstance("EUR"),
                InvoiceStatus.OVERDUE
        );

        Invoice invoice = invoiceMapper.toDomain(entity);


        assertEquals(id, invoice.getId());
        assertEquals("test AS", invoice.getCustomerName());
        assertEquals(amount, invoice.getAmount());
        assertEquals(Currency.getInstance("EUR"), invoice.getCurrency());
        assertEquals(InvoiceStatus.OVERDUE, invoice.getStatus());
    }
}
