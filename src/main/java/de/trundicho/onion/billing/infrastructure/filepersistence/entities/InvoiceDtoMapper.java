package de.trundicho.onion.billing.infrastructure.filepersistence.entities;

import org.mapstruct.Mapper;

import de.trundicho.onion.billing.domain.model.Invoice;

@Mapper
public interface InvoiceDtoMapper {

    Invoice toInvoice(InvoiceDto invoiceDto);

    InvoiceDto toInvoiceEntity(Invoice invoice);
}
