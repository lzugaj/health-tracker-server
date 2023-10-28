create sequence seq_refresh_token start 1 increment 1;

create table if not exists "refresh_token" (
    id bigint not null,
    user_id bigint not null,
    token uuid not null,
    expiry_date timestamp not null,
    primary key (id)
);

alter table refresh_token
    add constraint fk_user_refresh_token
        foreign key (user_id) references "users" (id);

alter table refresh_token
    add constraint uq_refresh_token
        unique (token);