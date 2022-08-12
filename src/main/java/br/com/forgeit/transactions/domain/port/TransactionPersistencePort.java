package br.com.forgeit.transactions.domain.port;

import java.util.Optional;

public interface TransactionPersistencePort {

    Optional<String> findByInvoiceIdWithPessimisticLocking(String id);

    Optional<String> findByInvoiceId(String id);

    void updateStatus(String invoiceId, String newStatus);

}
