package br.com.forgeit.transactions.infra.postgres;

import br.com.forgeit.transactions.domain.port.TransactionPersistencePort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionJpaAdapter implements TransactionPersistencePort {

    private final TransactionRepository transactionRepository;

    @Override
    public Optional<String> findByInvoiceIdWithPessimisticLocking(String id) {
        return  transactionRepository.findWithLockingByInvoiceId(id).map(TransactionEntity::getStatus);
    }

    @Override
    public Optional<String> findByInvoiceId(String id) {
        return  transactionRepository.findWithoutLockingByInvoiceId(id).map(TransactionEntity::getStatus);
    }

    @Override
    public void updateStatus(String invoiceId, String newStatus) {
        var entity = transactionRepository.findByInvoiceId(invoiceId);
        entity.setStatus(newStatus);
        transactionRepository.save(entity);
    }

}
