package de.trundicho.onion.billing.infrastructure.database.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import de.trundicho.onion.billing.infrastructure.database.DBRepository;
import de.trundicho.onion.billing.infrastructure.database.DatabaseAdapter;
import de.trundicho.onion.billing.infrastructure.database.entities.InvoiceEntityMapper;
import de.trundicho.onion.billing.service.PersistenceApi;

@EnableJpaRepositories(basePackages = "de.trundicho.onion.billing.infrastructure.database")
@EntityScan(basePackages = "de.trundicho.onion.billing.infrastructure.database")
@Configuration
public class DatabaseConfig {

    @Bean
    @Qualifier("database")
    public PersistenceApi getDatabase(DBRepository dbRepository, InvoiceEntityMapper mapper) {
        return new DatabaseAdapter(dbRepository, mapper);
    }

}
