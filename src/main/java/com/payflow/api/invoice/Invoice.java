package com.payflow.api.invoice;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public class Invoice {
    private final UUID id;
    private final String customerName;
    private final BigDecimal amount;
    private final Currency currency;
    private InvoiceStatus status;


    public Invoice(String customerName, BigDecimal amount, Currency currency) {
        if (customerName == null || customerName.isBlank()) {
            throw new IllegalArgumentException(
                    "Customer name cannot be blank"
            );
        }
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero"
            );
        }

        if (currency == null) {
            throw new IllegalArgumentException(
                    "Currency cannot be null"
            );
        }

        this.id = UUID.randomUUID();
        this.customerName = customerName;
        this.amount = amount;
        this.currency = currency;
        this.status = InvoiceStatus.PENDING;

    }

    // Restoration constructor

    public Invoice(UUID id, String customerName, BigDecimal amount, Currency currency, InvoiceStatus status) {

        if (id == null) {
            throw new IllegalArgumentException("Invoice id cannot be null");
        }
        if (customerName == null || customerName.isBlank()) {
            throw new IllegalArgumentException("Customer name cannot be blank");
        }
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        if (currency == null) {
            throw new IllegalArgumentException("Currency cannot be null");
        }
        if (status == null) {
            throw new IllegalArgumentException("Invoice status cannot be null");
        }

        this.id = id;
        this.customerName = customerName;
        this.amount = amount;
        this.currency = currency;
        this.status = status;

    }

    public UUID getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void markAsPaid() {
        if (status == InvoiceStatus.PAID) {
            throw new IllegalStateException("Invoice is already paid");
        }
        status = InvoiceStatus.PAID;
    }

    public void markAsOverdue() {
        if (status != InvoiceStatus.PENDING) {
            throw new IllegalStateException("Only pending invoices can be marked as overdue");
        }
        status = InvoiceStatus.OVERDUE;
    }


}