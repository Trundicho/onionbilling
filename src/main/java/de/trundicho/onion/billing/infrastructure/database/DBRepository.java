package de.trundicho.onion.billing.infrastructure.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.trundicho.onion.billing.infrastructure.database.entities.InvoiceEntity;

@Repository
public interface DBRepository extends CrudRepository<InvoiceEntity, Long> {

}
