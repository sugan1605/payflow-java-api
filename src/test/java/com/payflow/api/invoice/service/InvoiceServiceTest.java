package com.payflow.api.invoice.service;

import com.payflow.api.invoice.Invoice;
import com.payflow.api.invoice.InvoiceStatus;
import com.payflow.api.invoice.persistence.InvoiceEntity;
import com.payflow.api.invoice.persistence.InvoiceMapper;
import com.payflow.api.invoice.persistence.InvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InvoiceServiceTest {

    private InvoiceRepository invoiceRepository;
    private InvoiceService invoiceService;

    @BeforeEach
    void setUp() {
        invoiceRepository = mock(InvoiceRepository.class);
        InvoiceMapper invoiceMapper = new InvoiceMapper();
        invoiceService = new InvoiceService(
                invoiceRepository,
                invoiceMapper
        );
    }

    @Test
    void findAll_returnsEmptyList_whenNoInvoicesExist() {
        when(invoiceRepository.findAll()).thenReturn(List.of());

        List<Invoice> invoices = invoiceService.findAll();

        assertEquals(0, invoices.size());
    }


    @Test
    void findAll_returnsMappedInvoices_whenInvoicesExist() {
        InvoiceEntity entity = new InvoiceEntity(
                UUID.randomUUID(),
                "Acme Ltd",
                new BigDecimal("125.00"),
                Currency.getInstance("NOK"),
                InvoiceStatus.PENDING
        );

        when(invoiceRepository.findAll()).thenReturn(List.of(entity));

        List<Invoice> invoices = invoiceService.findAll();

        assertEquals(1, invoices.size());
        assertEquals("Acme Ltd", invoices.getFirst().getCustomerName());
        assertEquals(new BigDecimal("125.00"), invoices.getFirst().getAmount());
        assertEquals(InvoiceStatus.PENDING, invoices.getFirst().getStatus());
    }


    @Test
    void findById_returnsInvoice_whenInvoiceExists() {
        UUID id = UUID.randomUUID();

        InvoiceEntity entity = new InvoiceEntity(
                id,
                "Acer Ltd",
                new BigDecimal("125.00"),
                Currency.getInstance("NOK"),
                InvoiceStatus.PENDING
        );

        when(invoiceRepository.findById(id))
                .thenReturn(Optional.of(entity));

        Invoice invoice = invoiceService.findById(id);

        assertEquals(id, invoice.getId());
        assertEquals("Acer Ltd", invoice.getCustomerName());
        assertEquals(new BigDecimal("125.00"), invoice.getAmount());
    }

    @Test
    void findById_throwsException_whenInvoiceDoesNotExist() {
        UUID id = UUID.randomUUID();

        when(invoiceRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                InvoiceNotFoundException.class,
                () -> invoiceService.findById(id)
        );
    }

    @Test
    void save_returnsSavedInvoice_whenInvoiceIsSaved() {
        // Arrange

        Invoice invoice = new Invoice(
                "Acer Ltd",
                new BigDecimal("125.00"),
                Currency.getInstance("NOK")
        );

        InvoiceMapper mapper = new InvoiceMapper();
        InvoiceEntity entity = mapper.toEntity(invoice);

        when(invoiceRepository.save(any(InvoiceEntity.class))).thenReturn(entity);

        // Act
        Invoice savedInvoice = invoiceService.save(invoice);

        // Assert

        assertEquals(invoice.getId(), savedInvoice.getId());
        assertEquals("Acer Ltd", savedInvoice.getCustomerName());
        assertEquals(new BigDecimal("125.00"), savedInvoice.getAmount());
        assertEquals(InvoiceStatus.PENDING, savedInvoice.getStatus());

        verify(invoiceRepository).save(any(InvoiceEntity.class));
    }

}
