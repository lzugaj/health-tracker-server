CREATE SEQUENCE seq_body_mass_index START 1 INCREMENT 1;

CREATE TABLE IF NOT EXISTS "body_mass_index" (
    id BIGINT NOT NULL,
    height TEXT NOT NULL,
    weight TEXT NOT NULL,
    value TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    modified_at TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);