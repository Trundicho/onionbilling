package de.trundicho.onion.billing.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import de.trundicho.onion.billing.domain.model.Invoice;
import de.trundicho.onion.billing.domain.model.InvoiceState;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BillingClient {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    public List<Invoice> getAllInvoices() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/invoices"))
                                          .andExpect(MockMvcResultMatchers.status().isOk())
                                          .andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {

        });
    }

    public Invoice insert(String invoiceName) throws Exception {
        Invoice invoice = new Invoice(invoiceName, InvoiceState.TODO);
        String content = objectMapper.writeValueAsString(invoice);
        MvcResult mvcResult = this.mockMvc.perform(
                                          MockMvcRequestBuilders.post("/invoices").content(content).contentType("application/json"))
                                          .andExpect(MockMvcResultMatchers.status().isCreated())
                                          .andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {

        });
    }

    public Invoice update(Long invoiceId, Invoice invoice) throws Exception {
        String invoiceJson = objectMapper.writeValueAsString(invoice);
        MvcResult mvcResult = this.mockMvc.perform(
                                          MockMvcRequestBuilders.put("/invoices/" + invoiceId).content(invoiceJson).contentType("application/json"))
                                          .andExpect(MockMvcResultMatchers.status().isOk())
                                          .andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {

        });
    }

    public Invoice getInvoice(Long invoiceId) throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/invoices/" + invoiceId))
                                          .andExpect(MockMvcResultMatchers.status().isOk())
                                          .andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {

        });
    }

    public void remove(Long invoiceId) {
        try {
            this.mockMvc.perform(MockMvcRequestBuilders.delete("/invoices/" + invoiceId))
                        .andExpect(MockMvcResultMatchers.status().isNoContent());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void getInvoiceAndExpectNotFound(long invoiceId) throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/invoices/" + invoiceId)).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
