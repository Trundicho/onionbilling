package de.trundicho.onion.billing.infrastructure.filepersistence.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import de.trundicho.onion.billing.domain.model.InvoiceState;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceEntity implements Serializable {
    private String name;
    private Long id;
    private InvoiceState invoiceState;

}
