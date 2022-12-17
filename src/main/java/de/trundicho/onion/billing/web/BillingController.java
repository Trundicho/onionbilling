package de.trundicho.onion.billing.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.trundicho.onion.billing.domain.model.Invoice;
import de.trundicho.onion.billing.service.BillingService;
import de.trundicho.onion.billing.web.error.InvoiceNotFoundException;

@RestController
public class BillingController {

    private final BillingService invoiceList;

    @Autowired
    public BillingController(BillingService invoiceList) {
        this.invoiceList = invoiceList;
    }

    @RequestMapping(value = "/invoice/{id}", method = RequestMethod.GET, produces = "application/json")
    public Invoice getInvoice(@PathVariable Long id) {
        return Optional.ofNullable(invoiceList.getInvoice(id)).orElseThrow(() -> new InvoiceNotFoundException(id));
    }

    @RequestMapping(value = "/invoice/{id}", method = RequestMethod.DELETE)
    public void deleteInvoice(@PathVariable Long id) {
        invoiceList.deleteInvoice(id);
    }

    @RequestMapping(value = "/invoices", method = RequestMethod.GET, produces = "application/json")
    public List<Invoice> getAllInvoices() {
        return invoiceList.getAll();
    }

    @RequestMapping(value = "/invoice/{name}", method = RequestMethod.POST, produces = "application/json")
    public Invoice insertInvoice(@PathVariable(name = "name") String invoiceName) {
        return invoiceList.insertInvoice(invoiceName);
    }

    @RequestMapping(value = "/invoice/{id}", method = RequestMethod.PUT, produces = "application/json")
    public Invoice updateInvoice(@PathVariable Long id, @RequestBody Invoice invoice) {
        return invoiceList.update(id, invoice);
    }
}