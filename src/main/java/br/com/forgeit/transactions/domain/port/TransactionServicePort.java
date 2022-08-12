package br.com.forgeit.transactions.domain.port;

public interface TransactionServicePort {

    void updateStatusPending1WithLocking() throws Exception;

    void updateStatusPending2WithLocking() throws Exception;

    void updateStatusPending1() throws Exception;

    void updateStatusPending2() throws Exception;

    void initStatus() throws Exception;

    String getStatus() throws Exception;

}
