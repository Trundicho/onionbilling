package de.trundicho.onion.billing.infrastructure.database;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.trundicho.onion.billing.domain.model.Invoice;
import de.trundicho.onion.billing.infrastructure.database.entities.InvoiceEntity;
import de.trundicho.onion.billing.infrastructure.database.entities.InvoiceEntityMapper;
import de.trundicho.onion.billing.service.PersistenceApi;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DatabaseAdapter implements PersistenceApi {

    private final DBRepository dbRepository;
    private final InvoiceEntityMapper mapper;

    @Autowired
    public DatabaseAdapter(DBRepository dbRepository, InvoiceEntityMapper mapper) {
        this.dbRepository = dbRepository;
        this.mapper = mapper;
    }

    @Override
    public Invoice getInvoice(Long id) {
        InvoiceEntity referenceById;
        try {
            referenceById = dbRepository.getReferenceById(id);
            String name = referenceById.getName();
        } catch (EntityNotFoundException e) {
            log.error("EntityNotFound " + id);
            return null;
        }
        return mapper.toInvoice(referenceById);
    }

    @Override
    public Invoice insertInvoice(Invoice invoice) {
        return mapper.toInvoice(dbRepository.save(mapper.toInvoiceEntity(invoice)));
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return dbRepository.findAll().stream().map(mapper::toInvoice).collect(Collectors.toList());
    }

    @Override
    public Invoice updateInvoice(Long id, Invoice invoice) {
        return mapper.toInvoice(dbRepository.save(mapper.toInvoiceEntity(invoice)));
    }

    @Override
    public void deleteInvoice(Long id) {
        dbRepository.deleteById(id);
    }
}
