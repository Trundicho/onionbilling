package de.trundicho.onion.billing.infrastructure.backend;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import de.trundicho.onion.billing.domain.model.Invoice;
import de.trundicho.onion.billing.service.PersistenceApi;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class InvoiceBackendService implements PersistenceApi {

    private final InvoiceBackendMapper invoiceBackendMapper;
    private final InvoiceBackendClient invoiceBackendClient;

    @Override
    public Invoice getInvoice(Long id) {
        Mono<InvoiceBackend> invoiceBackendMono = invoiceBackendClient.getInvoiceBackend(id);
        InvoiceBackend invoiceBackend = invoiceBackendMono.block();
        return invoiceBackendMapper.toInvoice(invoiceBackend);
    }

    @Override
    public Invoice insertInvoice(Invoice invoice) {
        Mono<InvoiceBackend> backendMono = invoiceBackendClient.insertInvoice(invoice);
        return invoiceBackendMapper.toInvoice(backendMono.block());
    }

    @Override
    public List<Invoice> getAllInvoices() {
        Flux<InvoiceBackend> invoiceBackendFlux = invoiceBackendClient.getInvoices();
        List<InvoiceBackend> invoiceBackend = invoiceBackendFlux.collectList().block();
        if (invoiceBackend != null) {
            return invoiceBackend.stream().map(invoiceBackendMapper::toInvoice).collect(Collectors.toList());
        }
        return List.of();
    }

    @Override
    public Invoice updateInvoice(Long id, Invoice invoice) {
        Mono<InvoiceBackend> update = invoiceBackendClient.update(id, invoice);
        return invoiceBackendMapper.toInvoice(update.block());
    }

    @Override
    public void deleteInvoice(Long id) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
