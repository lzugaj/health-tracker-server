alter table body_mass_index
    add column user_id bigint not null;

alter table body_mass_index
    add constraint fk_user_bmi
        foreign key (user_id) references "users" (id);