# Pessimistic Locking with Spring Boot and JPA

## Execution without locking

```
b.c.f.PessimisticLockingApplication      : Transaction without locking:
b.c.f.t.d.service.TransactionService     : Step 1 - updateStatusPending1
b.c.f.t.d.service.TransactionService     : Step 2 - updateStatusPending1 - get status: CREATED
b.c.f.t.d.service.TransactionService     : Step 3 - updateStatusPending1 - status changed
b.c.f.t.d.service.TransactionService     : Step 1 - updateStatusPending2
b.c.f.t.d.service.TransactionService     : Step 2 - updateStatusPending2 - get status: CREATED
b.c.f.t.d.service.TransactionService     : Step 3 - updateStatusPending2 - status changed
b.c.f.t.d.service.TransactionService     : Step 4 - updateStatusPending1 - status committed
b.c.f.t.d.service.TransactionService     : Step 4 - updateStatusPending2 - status committed
b.c.f.PessimisticLockingApplication      : Status after exec: PENDING_2, expected: PENDING_1
```

## Execution with pessimistic locking - @Lock(LockModeType.PESSIMISTIC_WRITE)

```
b.c.f.PessimisticLockingApplication      : Transaction with locking:
b.c.f.t.d.service.TransactionService     : Step 1 - Locking - updateStatusPending1
b.c.f.t.d.service.TransactionService     : Step 2 - Locking - updateStatusPending1 - get status: CREATED
b.c.f.t.d.service.TransactionService     : Step 3 - Locking - updateStatusPending1 - status changed
b.c.f.t.d.service.TransactionService     : Step 1 - Locking - updateStatusPending2
b.c.f.t.d.service.TransactionService     : Step 4 - Locking - updateStatusPending1 - status committed
b.c.f.t.d.service.TransactionService     : Step 2 - Locking - updateStatusPending2 - get status: PENDING_1
b.c.f.t.d.service.TransactionService     : Step 4 - Locking - updateStatusPending2 - invalid status
b.c.f.PessimisticLockingApplication      : Status after exec: PENDING_1, expected: PENDING_1
```