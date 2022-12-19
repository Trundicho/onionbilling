package de.trundicho.onion.billing.infrastructure.database.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import de.trundicho.onion.billing.domain.model.InvoiceState;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceEntity implements Serializable {
    private String name;
    @Id
    @GeneratedValue
    private Long id;
    private InvoiceState invoiceState;

}
