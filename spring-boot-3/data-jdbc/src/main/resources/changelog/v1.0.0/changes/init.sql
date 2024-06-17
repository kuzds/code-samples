--liquibase formatted sql

--changeset a.lebedev:1.0.0-create-nspk_transactions
create table "users"
(

    id                  uuid default gen_random_uuid() not null constraint users_pk primary key,
    birth_date_time     timestamp,
    birth_date          date,
    bro                 varchar
);