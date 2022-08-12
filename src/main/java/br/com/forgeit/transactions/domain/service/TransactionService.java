package br.com.forgeit.transactions.domain.service;

import br.com.forgeit.transactions.domain.port.TransactionPersistencePort;
import br.com.forgeit.transactions.domain.port.TransactionServicePort;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionService implements TransactionServicePort {

    private final TransactionPersistencePort transactionPersistencePort;
    private static final String INVOICE_ID = "1234-5678";

    @Override
    @Transactional
    public void updateStatusPending1WithLocking() throws Exception {
        log.info("Step 1 - Locking - updateStatusPending1");
        var status = transactionPersistencePort.findByInvoiceIdWithPessimisticLocking(INVOICE_ID)
                        .orElseThrow(Exception::new);

        log.info("Step 2 - Locking - updateStatusPending1 - get status: {}", status);
        if ("CREATED".equals(status)) {
            transactionPersistencePort.updateStatus(INVOICE_ID, "PENDING_1");
            log.info("Step 3 - Locking - updateStatusPending1 - status changed");

            Thread.sleep(5000);
            log.info("Step 4 - Locking - updateStatusPending1 - status committed");
        }
    }

    @Override
    @Transactional
    public void updateStatusPending2WithLocking() throws Exception {
        Thread.sleep(1000);
        log.info("Step 1 - Locking - updateStatusPending2");
        var status = transactionPersistencePort.findByInvoiceIdWithPessimisticLocking(INVOICE_ID)
                .orElseThrow(Exception::new);

        log.info("Step 2 - Locking - updateStatusPending2 - get status: {}", status);
        if ("CREATED".equals(status)) {
            transactionPersistencePort.updateStatus(INVOICE_ID, "PENDING_2");
            log.info("Step 3 - Locking - updateStatusPending2 - status changed");

            Thread.sleep(4000);
            log.info("Step 4 - Locking - updateStatusPending2 - status committed");
        } else {
            log.info("Step 4 - Locking - updateStatusPending2 - invalid status");
        }
    }

    @Override
    @Transactional
    public void updateStatusPending1() throws Exception {
        log.info("Step 1 - updateStatusPending1");
        var status = transactionPersistencePort.findByInvoiceId(INVOICE_ID)
                .orElseThrow(Exception::new);

        log.info("Step 2 - updateStatusPending1 - get status: {}", status);
        if ("CREATED".equals(status)) {
            transactionPersistencePort.updateStatus(INVOICE_ID, "PENDING_1");
            log.info("Step 3 - updateStatusPending1 - status changed");

            Thread.sleep(5000);
            log.info("Step 4 - updateStatusPending1 - status committed");
        }
    }

    @Override
    @Transactional
    public void updateStatusPending2() throws Exception {
        Thread.sleep(1000);
        log.info("Step 1 - updateStatusPending2");
        var status = transactionPersistencePort.findByInvoiceId(INVOICE_ID)
                .orElseThrow(Exception::new);

        log.info("Step 2 - updateStatusPending2 - get status: {}", status);
        if ("CREATED".equals(status)) {
            transactionPersistencePort.updateStatus(INVOICE_ID, "PENDING_2");
            log.info("Step 3 - updateStatusPending2 - status changed");

            Thread.sleep(4000);
            log.info("Step 4 - updateStatusPending2 - status committed");
        } else {
            log.info("Step 4 - updateStatusPending2 - invalid status");
        }
    }

    @Override
    public void initStatus() throws Exception {
        transactionPersistencePort.updateStatus(INVOICE_ID, "CREATED");
    }

    @Override
    public String getStatus() throws Exception {
        return transactionPersistencePort.findByInvoiceId(INVOICE_ID)
                .orElseThrow(Exception::new);
    }
}
