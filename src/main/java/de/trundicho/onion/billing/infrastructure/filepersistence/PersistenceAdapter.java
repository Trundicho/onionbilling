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

import de.trundicho.onion.billing.infrastructure.filepersistence.entities.InvoiceDto;
import de.trundicho.onion.billing.infrastructure.filepersistence.entities.InvoiceDtoMapper;
import de.trundicho.onion.billing.domain.model.Invoice;
import de.trundicho.onion.billing.service.PersistenceApi;

@Service
public class PersistenceAdapter implements PersistenceApi {

    private final InvoiceDtoMapper invoiceDtoMapper;
    private final Map<Long, InvoiceDto> invoiceList = new ConcurrentHashMap<>();
    private final InvoiceReaderAndWriter invoiceReaderAndWriter;

    @Autowired
    public PersistenceAdapter(InvoiceReaderAndWriter invoiceReaderAndWriter) {
        this.invoiceReaderAndWriter = invoiceReaderAndWriter;
        this.invoiceDtoMapper = Mappers.getMapper(InvoiceDtoMapper.class);
        Collection<InvoiceDto> invoiceEntities = invoiceReaderAndWriter.read();
        invoiceEntities.forEach(invoice -> invoiceList.put(invoice.getId(), invoice));
    }

    @PreDestroy
    public void destroy() {
        invoiceReaderAndWriter.write(invoiceList.values());
    }

    @Override
    public Invoice getInvoice(Long id) {
        InvoiceDto invoiceDto = invoiceList.get(id);
        return invoiceDtoMapper.toInvoice(invoiceDto);
    }

    @Override
    public Invoice insertInvoice(Invoice invoice) {
        Long invoiceId = createId();
        InvoiceDto persisted = new InvoiceDto(invoice.getName(), invoiceId, invoice.getInvoiceState());
        invoiceList.put(invoiceId, persisted);
        return invoiceDtoMapper.toInvoice(persisted);
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceList.values().stream().map(invoiceDtoMapper::toInvoice).collect(Collectors.toList());
    }

    @Override
    public Invoice updateInvoice(Long id, Invoice invoice) {
        invoice.setId(id);
        InvoiceDto invoiceDto = invoiceDtoMapper.toInvoiceEntity(invoice);
        invoiceList.put(id, invoiceDto);
        return invoiceDtoMapper.toInvoice(invoiceList.get(id));
    }

    @Override
    public void deleteInvoice(Long id) {
        invoiceList.remove(id);
    }

    private long createId() {
        return invoiceList.size() + 1l;
    }
}
