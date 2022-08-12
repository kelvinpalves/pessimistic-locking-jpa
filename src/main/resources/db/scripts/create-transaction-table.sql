create table transactions
(
    id bigserial primary key,
    invoice_id varchar not null unique,
    amount integer not null,
    status varchar not null
);
