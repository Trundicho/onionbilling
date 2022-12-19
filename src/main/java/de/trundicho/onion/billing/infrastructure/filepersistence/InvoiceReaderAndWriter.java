package de.trundicho.onion.billing.infrastructure.filepersistence;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import de.trundicho.onion.billing.infrastructure.filepersistence.entities.InvoiceDto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class InvoiceReaderAndWriter {
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${persistence.file}")
    private String persistenceFile;

    public void write(Collection<InvoiceDto> invoices) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(persistenceFile), invoices);
        } catch (IOException e) {
            log.error("Can not write to file " + e.getMessage());
        }
    }

    public Collection<InvoiceDto> read() {
        try {
            return objectMapper.readValue(new File(persistenceFile), new TypeReference<>() {

            });
        } catch (IOException e) {
            log.error("Can not read from file " + e.getMessage());
        }
        return Collections.emptyList();
    }
}
