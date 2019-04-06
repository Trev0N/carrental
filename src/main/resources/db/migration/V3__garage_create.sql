DROP TABLE IF EXISTS carrental.garage;

DROP SEQUENCE IF EXISTS carrental.garage_seq_id;

CREATE SEQUENCE carrental.garage_seq_id;

CREATE TABLE carrental.garage (
                                "id"           BIGINT                   NOT NULL DEFAULT nextval('carrental.garage_seq_id'),
                                "name"         VARCHAR(255)             NOT NULL,
                                "address"      VARCHAR(255)             NOT NULL
);

ALTER TABLE carrental.garage
  ADD CONSTRAINT garage_id_pk PRIMARY KEY (id);

CREATE UNIQUE INDEX carrental_garage_seq_id
  ON carrental.garage (id);