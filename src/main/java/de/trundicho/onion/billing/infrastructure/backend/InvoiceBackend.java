package de.trundicho.onion.billing.infrastructure.backend;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import de.trundicho.onion.billing.domain.model.InvoiceState;

@Data
@NoArgsConstructor
public class InvoiceBackend implements Serializable {
    private String name;
    private Long id;
    private InvoiceState invoiceState;
}
