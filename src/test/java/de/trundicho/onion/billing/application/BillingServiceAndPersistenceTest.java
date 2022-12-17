package de.trundicho.onion.billing.application;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import de.trundicho.onion.billing.domain.model.Invoice;
import de.trundicho.onion.billing.domain.model.InvoiceState;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BillingServiceAndPersistenceTest {
    @Autowired
    BillingClient billingClient;

    @AfterEach
    void clearDatabase() throws Exception {
        List<Invoice> allInvoice = billingClient.getAllInvoices();
        allInvoice.forEach(invoice -> billingClient.remove(invoice.getId()));
    }

    @Test
    void insertInvoice_and_loadInvoice() throws Exception {
        Invoice invoice = billingClient.insert("Buy me on monday");
        Invoice reloaded = billingClient.getInvoice(invoice.getId());
        assertEquals("Buy me on monday", reloaded.getName());
    }

    @Test
    void updateInvoiceName() throws Exception {
        Invoice invoice = billingClient.insert("Buy me on friday");
        Long invoiceId = invoice.getId();
        Invoice reloaded = billingClient.update(invoiceId, new Invoice("Buy me on saturday", InvoiceState.TODO));
        assertEquals("Buy me on saturday", reloaded.getName());
    }

    @Test
    void updateInvoiceState() throws Exception {
        Invoice invoice = billingClient.insert("Buy me on friday");
        assertEquals("TODO", invoice.getInvoiceState().name());
        Long invoiceId = invoice.getId();
        Invoice reloaded = billingClient.update(invoiceId, new Invoice("Buy me on saturday", InvoiceState.DONE));
        assertEquals("DONE", reloaded.getInvoiceState().name());
    }

    @Test
    void getAllInvoices() throws Exception {
        billingClient.insert("1");
        billingClient.insert("2");
        billingClient.insert("3");
        int size = billingClient.getAllInvoices().size();
        assertEquals(3, size);
    }

    @Test
    void invoiceNotFound() throws Exception {
        billingClient.getInvoiceAndExpectNotFound(1000L);
    }

}
