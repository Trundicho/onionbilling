package de.trundicho.onion.billing.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    private String name;
    private Long id;
    private InvoiceState invoiceState;

    public Invoice(String name, InvoiceState invoiceState) {
        this(name, null, invoiceState);
    }
}
