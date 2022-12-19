package de.trundicho.onion.billing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import de.trundicho.onion.billing.domain.model.Invoice;

/**
 * Business logic
 */
@Component
public class BillingService {

//    @Qualifier("backend")
//    @Qualifier("filepersistence")
    @Qualifier("database")
    @Autowired
    private PersistenceApi persistenceApi;


    public Invoice getInvoice(Long id) {
        return persistenceApi.getInvoice(id);
    }

    public Invoice insertInvoice(Invoice invoice) {
        return persistenceApi.insertInvoice(invoice);
    }

    public List<Invoice> getAll() {
        return persistenceApi.getAllInvoices();
    }

    public Invoice update(Long id, Invoice invoice) {
        return persistenceApi.updateInvoice(id, invoice);
    }

    public void deleteInvoice(Long id) {
        persistenceApi.deleteInvoice(id);
    }
}
