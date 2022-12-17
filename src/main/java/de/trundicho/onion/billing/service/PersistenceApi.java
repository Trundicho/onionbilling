package de.trundicho.onion.billing.service;

import java.util.List;

import de.trundicho.onion.billing.domain.model.Invoice;

public interface PersistenceApi {
    Invoice getInvoice(Long id);

    Invoice insertInvoice(Invoice invoice);

    List<Invoice> getAllInvoices();

    Invoice updateInvoice(Long id, Invoice invoice);

    void deleteInvoice(Long id);
}
