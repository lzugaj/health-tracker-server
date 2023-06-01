create sequence seq_body_mass_index start 1 increment 1;

create table if not exists "body_mass_index" (
    id bigint not null,
    height text not null,
    weight text not null,
    value text not null,
    created_at timestamp not null,
    modified_at timestamp not null,
    primary key (id)
);