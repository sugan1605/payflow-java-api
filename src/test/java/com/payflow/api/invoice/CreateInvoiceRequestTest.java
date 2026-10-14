package com.payflow.api.invoice;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


//Using this test to check the request's validation rules:

public class CreateInvoiceRequestTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    public static void tearDown() {
        factory.close();
    }

    @Test
    void shouldAcceptValidRequest() {
        var request = new CreateInvoiceRequest(
                "Acme AS",
                new BigDecimal("1250.00"),
                "NOK"
        );

        assertTrue(validator.validate(request).isEmpty());
    }

    @Test
    void shouldRejectBlankCustomerName() {
        var request = new CreateInvoiceRequest(
                " ",
                new BigDecimal("1250.00"),
                "NOK"
        );

        assertEquals(1, validator.validate(request).size());
    }

    @Test
    void shouldRejectZeroAmount() {
        var request = new CreateInvoiceRequest(
                "Acme AS",
                BigDecimal.ZERO,
                "NOK"
        );
        assertEquals(1, validator.validate(request).size());
    }

    @Test
    void shouldRejectMissingAmount() {
        var request = new CreateInvoiceRequest(
                "Acme AS",
                null,
                "NOK"
        );

        var violations = validator.validate(request);

        assertTrue(
                violations.stream().anyMatch(
                        violation ->
                                violation.getPropertyPath().toString().equals("amount")
                ),
                "Expected a validation violation for amount"
        );
    }
}

