create sequence seq_body_mass_index start 1 increment 1;

create table if not exists "body_mass_index" (
    id bigint not null,
    height numeric(5, 2) not null,
    weight numeric(5, 2) not null,
    value numeric(5, 2) not null,
    age int not null,
    gender text not null,
    body_mass_index_type text not null,
    created_at timestamp not null,
    last_modified_at timestamp,
    created_by int not null,
    last_modified_by int,
    primary key (id)
);