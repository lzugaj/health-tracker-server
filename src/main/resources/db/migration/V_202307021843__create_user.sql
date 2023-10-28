create sequence seq_user_index start 1 increment 1;

create table if not exists "users" (
    id bigint not null,
    first_name text not null,
    last_name text not null,
    email text not null,
    role text not null,
    created_at timestamp not null,
    modified_at timestamp not null,
    primary key (id)
);