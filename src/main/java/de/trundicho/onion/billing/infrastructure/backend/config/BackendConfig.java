package de.trundicho.onion.billing.infrastructure.backend.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.trundicho.onion.billing.infrastructure.backend.InvoiceBackendClient;
import de.trundicho.onion.billing.infrastructure.backend.InvoiceBackendMapper;
import de.trundicho.onion.billing.infrastructure.backend.InvoiceBackendService;
import de.trundicho.onion.billing.service.PersistenceApi;

@Configuration
public class BackendConfig {

    @Bean
    @Qualifier("backend")
    public PersistenceApi getBackendPersistence(InvoiceBackendMapper invoiceBackendMapper,
            InvoiceBackendClient invoiceBackendClient) {
        return new InvoiceBackendService(invoiceBackendMapper, invoiceBackendClient);
    }
}
