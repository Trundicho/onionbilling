package de.trundicho.onion.billing.application;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockWebServer;
import de.trundicho.onion.billing.domain.model.Invoice;
import de.trundicho.onion.billing.domain.model.InvoiceState;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BillingServiceAndBackendTest {

    private static MockWebServerDispatcher backend;
    @Autowired
    BillingClient billingClient;
    @Autowired
    ObjectMapper objectMapper;

    private static MockWebServer mockBackend;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackend = new MockWebServer();
        mockBackend.start(8081);
        backend = new MockWebServerDispatcher();
        mockBackend.setDispatcher(backend);
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackend.shutdown();
    }

    @Test
    void insertInvoice_and_loadInvoice() throws Exception {
        String responseJsonFile = "./responses/InsertAndLoad.json";
        backend.setResponses(Map.of("POST/invoice", new MockResponse().setResponseJsonFile(responseJsonFile), "GET/invoice?id=1",
                new MockResponse().setResponseJsonFile(responseJsonFile)));
        Invoice invoice = billingClient.insert("Buy me on monday");
        Invoice reloaded = billingClient.getInvoice(invoice.getId());
        assertEquals("Buy me on monday", reloaded.getName());
    }

    @Test
    void getAllInvoices() throws Exception {
        backend.setResponses(Map.of("GET/invoices",
                new MockResponse().setHttpStatus(HttpStatus.OK).setResponseJsonFile("./responses/AllBackendInvoices.json")));
        int size = billingClient.getAllInvoices().size();
        assertEquals(3, size);
    }

    @Test
    void updateInvoiceName() throws Exception {
        backend.setResponses(Map.of("PUT/invoice?id=1", new MockResponse().setResponseJsonFile("./responses/Update.json")));
        Long invoiceId = 1L;
        Invoice reloaded = billingClient.update(invoiceId, new Invoice("Buy me on monday", InvoiceState.TODO));
        assertEquals("Buy me on monday", reloaded.getName());
    }

    @Test
    void invoiceNotFound() throws Exception {
        backend.setResponses(Map.of("GET/invoice?id=1000", new MockResponse().setHttpStatus(HttpStatus.NOT_FOUND)));
        billingClient.getInvoiceAndExpectNotFound(1000L);
    }

}
