package com.payflow.api.invoice;

public class InvalidCurrencyCodeException extends RuntimeException {

    public InvalidCurrencyCodeException(String currencyCode) {
        super("Invalid currency code: " + currencyCode);
    }
}
