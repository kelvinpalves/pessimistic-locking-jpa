# Pessimistic Locking with Spring Boot and JPA

## Execution without locking

```
2022-08-12 02:59:12.855  INFO 9881 --- [           main] b.c.f.PessimisticLockingApplication      : Transaction without locking:
2022-08-12 02:59:12.856  INFO 9881 --- [pool-1-thread-1] b.c.f.t.d.service.TransactionService     : Step 1 - updateStatusPending1
2022-08-12 02:59:12.858  INFO 9881 --- [pool-1-thread-1] b.c.f.t.d.service.TransactionService     : Step 2 - updateStatusPending1 - get status: CREATED
2022-08-12 02:59:12.859  INFO 9881 --- [pool-1-thread-1] b.c.f.t.d.service.TransactionService     : Step 3 - updateStatusPending1 - status changed
2022-08-12 02:59:13.857  INFO 9881 --- [pool-1-thread-2] b.c.f.t.d.service.TransactionService     : Step 1 - updateStatusPending2
2022-08-12 02:59:13.863  INFO 9881 --- [pool-1-thread-2] b.c.f.t.d.service.TransactionService     : Step 2 - updateStatusPending2 - get status: CREATED
2022-08-12 02:59:13.866  INFO 9881 --- [pool-1-thread-2] b.c.f.t.d.service.TransactionService     : Step 3 - updateStatusPending2 - status changed
2022-08-12 02:59:17.860  INFO 9881 --- [pool-1-thread-1] b.c.f.t.d.service.TransactionService     : Step 4 - updateStatusPending1 - status committed
2022-08-12 02:59:17.867  INFO 9881 --- [pool-1-thread-2] b.c.f.t.d.service.TransactionService     : Step 4 - updateStatusPending2 - status committed
2022-08-12 02:59:22.861  INFO 9881 --- [           main] b.c.f.PessimisticLockingApplication      : Status after exec: PENDING_2, expected: PENDING_1
```

## Execution with pessimistic locking - @Lock(LockModeType.PESSIMISTIC_WRITE)

```
2022-08-12 02:59:22.872  INFO 9881 --- [           main] b.c.f.PessimisticLockingApplication      : Transaction with locking:
2022-08-12 02:59:22.875  INFO 9881 --- [pool-2-thread-1] b.c.f.t.d.service.TransactionService     : Step 1 - Locking - updateStatusPending1
2022-08-12 02:59:22.884  INFO 9881 --- [pool-2-thread-1] b.c.f.t.d.service.TransactionService     : Step 2 - Locking - updateStatusPending1 - get status: CREATED
2022-08-12 02:59:22.887  INFO 9881 --- [pool-2-thread-1] b.c.f.t.d.service.TransactionService     : Step 3 - Locking - updateStatusPending1 - status changed
2022-08-12 02:59:23.877  INFO 9881 --- [pool-2-thread-2] b.c.f.t.d.service.TransactionService     : Step 1 - Locking - updateStatusPending2
2022-08-12 02:59:27.888  INFO 9881 --- [pool-2-thread-1] b.c.f.t.d.service.TransactionService     : Step 4 - Locking - updateStatusPending1 - status committed
2022-08-12 02:59:27.896  INFO 9881 --- [pool-2-thread-2] b.c.f.t.d.service.TransactionService     : Step 2 - Locking - updateStatusPending2 - get status: PENDING_1
2022-08-12 02:59:27.897  INFO 9881 --- [pool-2-thread-2] b.c.f.t.d.service.TransactionService     : Step 4 - Locking - updateStatusPending2 - invalid status
2022-08-12 02:59:32.879  INFO 9881 --- [           main] b.c.f.PessimisticLockingApplication      : Status after exec: PENDING_1, expected: PENDING_1
```