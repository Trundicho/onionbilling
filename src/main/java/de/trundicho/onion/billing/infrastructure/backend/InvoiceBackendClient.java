package de.trundicho.onion.billing.infrastructure.backend;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import de.trundicho.onion.billing.domain.model.Invoice;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class InvoiceBackendClient {

    private final String basePath = "http://localhost:8081";

    public Mono<InvoiceBackend> getInvoiceBackend(Long id) {
        return WebClient.create().get().uri(basePath + "/invoice?id=" + id)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, (c) -> Mono.empty())
                .bodyToMono(InvoiceBackend.class);
    }

    public Flux<InvoiceBackend> getInvoices() {
        return WebClient.create().get().uri(basePath + "/invoices")
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, (c) -> Mono.empty())
                .bodyToFlux(InvoiceBackend.class);
    }

    public Mono<InvoiceBackend> update(Long id, Invoice invoice) {
        return WebClient.create().put().uri(basePath + "/invoice?id=" + id).bodyValue(invoice)
                        .retrieve()
                        .bodyToMono(InvoiceBackend.class);

    }

    public Mono<InvoiceBackend> insertInvoice(Invoice invoice) {
        return WebClient.create().post().uri(basePath + "/invoice").bodyValue(invoice)
                        .retrieve()
                        .bodyToMono(InvoiceBackend.class);
    }
}
