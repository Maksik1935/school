-- liquibase formatted sql

-- changeset mkachalov:1

CREATE INDEX name_and_colour_index ON faculty (name, colour)