package de.trundicho.onion.billing.infrastructure.filepersistence.entities;

import org.mapstruct.Mapper;

import de.trundicho.onion.billing.domain.model.Invoice;

@Mapper
public interface InvoiceEntityMapper {

    Invoice toInvoice(InvoiceEntity invoiceEntity);

    InvoiceEntity toInvoiceEntity(Invoice invoice);
}
