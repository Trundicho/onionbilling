package de.trundicho.onion.billing.web.error;

public class InvoiceNotFoundException extends RuntimeException {

    public InvoiceNotFoundException(Long id) {
        super("Could not find invoice " + id);
    }
}