package br.com.forgeit.transactions.infra.postgres;

import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<TransactionEntity> findWithLockingByInvoiceId(String invoiceId);

    Optional<TransactionEntity> findWithoutLockingByInvoiceId(String invoiceId);

    TransactionEntity findByInvoiceId(String invoiceId);

}
