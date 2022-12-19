package de.trundicho.onion.billing.infrastructure.filepersistence.entities;

import java.io.Serializable;

import de.trundicho.onion.billing.domain.model.InvoiceState;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDto implements Serializable {
    private String name;
    private Long id;
    private InvoiceState invoiceState;

}
