DROP TABLE IF EXISTS carrental.car;

DROP SEQUENCE IF EXISTS carrental.car_seq_id;

CREATE SEQUENCE carrental.car_seq_id;

CREATE TABLE carrental.car (
                                "id"           BIGINT                   NOT NULL DEFAULT nextval('carrental.car_seq_id'),
                                "mark"         VARCHAR(255)             NOT NULL,
                                "model"        VARCHAR(255)             NOT NULL,
                                "engine"       DOUBLE PRECISION         NOT NUll,
                                "power"        INTEGER                  NOT NULL ,
                                "created_at"    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
                                "modified_at"   TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

ALTER TABLE carrental.car
  ADD CONSTRAINT car_id_pk PRIMARY KEY (id);

CREATE UNIQUE INDEX carrental_car_seq_id
  ON carrental.car (id);