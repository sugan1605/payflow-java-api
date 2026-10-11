package com.payflow.api.invoice;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceTest {
    @Test
    void shouldCreateInvoiceWithValidDetails() {
        //Arrange
        Invoice invoice = new Invoice(
                "John Doe",
                new BigDecimal("1250.00"),
                Currency.getInstance("NOK")
        );

        //Assert

        assertNotNull(invoice.getId());
        assertEquals("John Doe", invoice.getCustomerName());
        assertEquals(0, new BigDecimal("1250.00").compareTo(invoice.getAmount()));
        assertEquals(Currency.getInstance("NOK"), invoice.getCurrency());
        assertEquals(InvoiceStatus.PENDING, invoice.getStatus());
    }

    @Test
    void shouldRejectBlankCustomerName() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Invoice(
                        " ",
                        new BigDecimal("1250.00"),
                        Currency.getInstance("NOK")
                )
        );
    }

    @Test
    void shouldRejectZeroAmount() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Invoice(
                        "Ola Normann",
                        BigDecimal.ZERO,
                        Currency.getInstance("NOK")
                )
        );
    }

    @Test
    void shouldRejectNullCurrency() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Invoice(
                        "John Doe",
                        new BigDecimal("1250.00"),
                        null
                )
        );
    }

    @Test
    void invoiceStatusMarkedAsPaid() {

        // Arrange
        Invoice invoice = new Invoice(
                "John Doe",
                new BigDecimal("1250.00"),
                Currency.getInstance("NOK")
        );

        // Act
        invoice.markAsPaid();

        // Assert

        assertEquals(InvoiceStatus.PAID, invoice.getStatus());
    }

    @Test
    void invoiceStatusMarkedAsOverdue() {
        Invoice invoice = new Invoice(
                "John Doe",
                new BigDecimal("1250.00"),
                Currency.getInstance("NOK")
        );

        invoice.markAsOverdue();

        assertEquals(InvoiceStatus.OVERDUE, invoice.getStatus());
    }


    @Test
    void shouldRejectMarkingPaidInvoiceAsOverdue() {
        Invoice invoice = new Invoice(
                "John Doe",
                new BigDecimal("1250.00"),
                Currency.getInstance("NOK")
        );
        invoice.markAsPaid();

        assertThrows(
                IllegalStateException.class,
                invoice::markAsOverdue
        );

        assertEquals(InvoiceStatus.PAID, invoice.getStatus());
    }

    @Test
    void shouldAllowOverdueInvoiceToBePaid() {
        Invoice invoice = new Invoice(
                "John Doe",
                new BigDecimal("1250.00"),
                Currency.getInstance("NOK")
        );

        invoice.markAsOverdue();
        invoice.markAsPaid();

        assertEquals(InvoiceStatus.PAID, invoice.getStatus());
    }
}
