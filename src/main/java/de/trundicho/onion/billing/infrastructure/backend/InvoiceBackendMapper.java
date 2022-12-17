package de.trundicho.onion.billing.infrastructure.backend;

import org.mapstruct.Mapper;

import de.trundicho.onion.billing.domain.model.Invoice;

@Mapper
public interface InvoiceBackendMapper {

    Invoice toInvoice(InvoiceBackend invoiceBackend);

    InvoiceBackend toInvoiceBackend(Invoice invoice);
}
