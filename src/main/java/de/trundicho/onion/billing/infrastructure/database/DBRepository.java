package de.trundicho.onion.billing.infrastructure.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.trundicho.onion.billing.infrastructure.database.entities.InvoiceEntity;

@Repository
public interface DBRepository extends JpaRepository<InvoiceEntity, Long> {

}
