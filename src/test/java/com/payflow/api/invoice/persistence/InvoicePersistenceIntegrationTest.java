package com.payflow.api.invoice.persistence;

import com.payflow.api.invoice.Invoice;
import com.payflow.api.invoice.InvoiceStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class InvoicePersistenceIntegrationTest {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Test
    void shouldSaveAndRetrieveInvoice() {
        // Arrange
        Invoice invoice = new Invoice(
                "Test Customer",
                new BigDecimal("125.50"),
                Currency.getInstance("NOK")
        );

        InvoiceEntity entity = invoiceMapper.toEntity(invoice);

        // Act
        InvoiceEntity savedEntity = invoiceRepository.saveAndFlush(entity);

        InvoiceEntity retrievedEntity = invoiceRepository
                .findById(savedEntity.getId())
                .orElseThrow();

        Invoice retrievedInvoice = invoiceMapper.toDomain(retrievedEntity);

        // Assert
        assertThat(retrievedInvoice.getId()).isEqualTo(invoice.getId());
        assertThat(retrievedInvoice.getCustomerName()).isEqualTo("Test Customer");
        assertThat(retrievedInvoice.getAmount())
                .isEqualByComparingTo("125.50");
        assertThat(retrievedInvoice.getCurrency())
                .isEqualTo(Currency.getInstance("NOK"));
        assertThat(retrievedInvoice.getStatus())
                .isEqualTo(InvoiceStatus.PENDING);
    }
}
