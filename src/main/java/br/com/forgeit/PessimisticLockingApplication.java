package br.com.forgeit;

import br.com.forgeit.transactions.domain.port.TransactionServicePort;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import liquibase.repackaged.org.apache.commons.lang3.function.FailableRunnable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class PessimisticLockingApplication implements CommandLineRunner {

    private final TransactionServicePort transactionService;

    public static void main(String[] args) {
        SpringApplication.run(PessimisticLockingApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        transactionService.initStatus();
        log.info("Transaction without locking:");

        ExecutorService executorWithLocking = Executors.newFixedThreadPool(2);
        executorWithLocking.execute(safeRunnable(transactionService::updateStatusPending1));
        executorWithLocking.execute(safeRunnable(transactionService::updateStatusPending2));
        executorWithLocking.shutdown();

        Thread.sleep(10000);
        log.info("Status after exec: {}, expected: {}\n", transactionService.getStatus(), "PENDING_1");

        transactionService.initStatus();
        log.info("Transaction with locking:");

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(safeRunnable(transactionService::updateStatusPending1WithLocking));
        executor.execute(safeRunnable(transactionService::updateStatusPending2WithLocking));
        executor.shutdown();

        Thread.sleep(10000);
        log.info("Status after exec: {}, expected: {}", transactionService.getStatus(), "PENDING_1");
    }

    private Runnable safeRunnable(FailableRunnable<Exception> runnable) {
        return () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
