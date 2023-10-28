create sequence seq_hypertension start 1 increment 1;

create table if not exists "hypertension" (
    id bigint not null,
    systolic integer not null,
    diastolic integer not null,
    user_id bigint not null,
    created_at timestamp not null,
    modified_at timestamp not null,
    primary key (id)
);

alter table hypertension
    add constraint fk_user_hypertension
        foreign key (user_id) references "users" (id);