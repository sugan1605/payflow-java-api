package com.payflow.api.invoice.persistence;

import com.payflow.api.invoice.InvoiceStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

@Entity
@Table(name = "invoices")
public class InvoiceEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(nullable = false, length = 3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvoiceStatus status;

    protected InvoiceEntity() {
        //This is required by JPA
    }

    public InvoiceEntity(UUID id, String customerName, BigDecimal amount, Currency currency, InvoiceStatus status) {
        this.id = id;
        this.customerName = customerName;
        this.amount = amount;
        this.currency = currency.getCurrencyCode();
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
        return Currency.getInstance(currency);
    }

    public InvoiceStatus getStatus() {
        return status;
    }
}
