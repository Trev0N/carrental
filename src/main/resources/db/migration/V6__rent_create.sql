DROP TABLE IF EXISTS carrental.rent;

DROP SEQUENCE IF EXISTS carrental.rent_seq_id;

CREATE SEQUENCE carrental.rent_seq_id;

CREATE TABLE carrental.rent (
                              "id"           BIGINT                   NOT NULL DEFAULT nextval('carrental.rent_seq_id'),
                              "user_id"      BIGINT                   NOT NULL REFERENCES carrental."user" (id),
                              "car_id"       BIGINT                   NOT NULL REFERENCES carrental."car" (id),
                              "rent_start_date"    TIMESTAMP WITH TIME ZONE  NOT NULL DEFAULT now(),
                              "rent_end_date" TIMESTAMP WITH TIME ZONE
                             );

ALTER TABLE carrental.rent
  ADD CONSTRAINT rent_id_pk PRIMARY KEY (id);

CREATE UNIQUE INDEX carrental_rent_seq_id
  ON carrental.rent (id);