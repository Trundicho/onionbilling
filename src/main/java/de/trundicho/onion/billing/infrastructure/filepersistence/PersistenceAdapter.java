package de.trundicho.onion.billing.infrastructure.filepersistence;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.annotation.PreDestroy;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.trundicho.onion.billing.infrastructure.filepersistence.entities.InvoiceEntity;
import de.trundicho.onion.billing.infrastructure.filepersistence.entities.InvoiceEntityMapper;
import de.trundicho.onion.billing.domain.model.Invoice;
import de.trundicho.onion.billing.service.PersistenceApi;

@Service
public class PersistenceAdapter implements PersistenceApi {

    private final InvoiceEntityMapper invoiceEntityMapper;
    private final Map<Long, InvoiceEntity> invoiceList = new ConcurrentHashMap<>();
    private final InvoiceReaderAndWriter invoiceReaderAndWriter;

    @Autowired
    public PersistenceAdapter(InvoiceReaderAndWriter invoiceReaderAndWriter) {
        this.invoiceReaderAndWriter = invoiceReaderAndWriter;
        this.invoiceEntityMapper = Mappers.getMapper(InvoiceEntityMapper.class);
        Collection<InvoiceEntity> invoiceEntities = invoiceReaderAndWriter.read();
        invoiceEntities.forEach(invoice -> invoiceList.put(invoice.getId(), invoice));
    }

    @PreDestroy
    public void destroy() {
        invoiceReaderAndWriter.write(invoiceList.values());
    }

    @Override
    public Invoice getInvoice(Long id) {
        InvoiceEntity invoiceEntity = invoiceList.get(id);
        return invoiceEntityMapper.toInvoice(invoiceEntity);
    }

    @Override
    public Invoice insertInvoice(Invoice invoice) {
        Long invoiceId = createId();
        InvoiceEntity persisted = new InvoiceEntity(invoice.getName(), invoiceId, invoice.getInvoiceState());
        invoiceList.put(invoiceId, persisted);
        return invoiceEntityMapper.toInvoice(persisted);
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceList.values().stream().map(invoiceEntityMapper::toInvoice).collect(Collectors.toList());
    }

    @Override
    public Invoice updateInvoice(Long id, Invoice invoice) {
        invoice.setId(id);
        InvoiceEntity invoiceEntity = invoiceEntityMapper.toInvoiceEntity(invoice);
        invoiceList.put(id, invoiceEntity);
        return invoiceEntityMapper.toInvoice(invoiceList.get(id));
    }

    @Override
    public void deleteInvoice(Long id) {
        invoiceList.remove(id);
    }

    private long createId() {
        return invoiceList.size() + 1l;
    }
}
