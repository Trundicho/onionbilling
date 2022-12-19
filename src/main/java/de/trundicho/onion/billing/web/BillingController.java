package de.trundicho.onion.billing.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.trundicho.onion.billing.domain.model.Invoice;
import de.trundicho.onion.billing.service.BillingService;
import de.trundicho.onion.billing.web.error.InvoiceNotFoundException;

@RestController
@RequestMapping("/invoices")
public class BillingController {

    private final BillingService invoiceList;

    @Autowired
    public BillingController(BillingService invoiceList) {
        this.invoiceList = invoiceList;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Invoice getInvoice(@PathVariable Long id) {
        return Optional.ofNullable(invoiceList.getInvoice(id)).orElseThrow(() -> new InvoiceNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable Long id) {
        invoiceList.deleteInvoice(id);
    }

    @GetMapping(produces = "application/json")
    public List<Invoice> getAllInvoices() {
        return invoiceList.getAll();
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Invoice insertInvoice(@RequestBody Invoice invoice) {
        return invoiceList.insertInvoice(invoice);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    public Invoice updateInvoice(@PathVariable Long id, @RequestBody Invoice invoice) {
        return invoiceList.update(id, invoice);
    }
}