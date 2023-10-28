alter table "users"
    add constraint uq_user_email
        unique (email);