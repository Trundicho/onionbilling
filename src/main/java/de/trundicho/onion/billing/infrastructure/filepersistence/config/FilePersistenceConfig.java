package de.trundicho.onion.billing.infrastructure.filepersistence.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.trundicho.onion.billing.infrastructure.filepersistence.InvoiceReaderAndWriter;
import de.trundicho.onion.billing.infrastructure.filepersistence.PersistenceAdapter;
import de.trundicho.onion.billing.service.PersistenceApi;

@Configuration
public class FilePersistenceConfig {

    @Bean
    @Qualifier("filepersistence")
    public PersistenceApi getFilePersistence(InvoiceReaderAndWriter invoiceReaderAndWriter) {
        return new PersistenceAdapter(invoiceReaderAndWriter);
    }

}
