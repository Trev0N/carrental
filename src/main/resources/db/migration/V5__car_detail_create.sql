DROP TABLE IF EXISTS carrental.car_detail;

DROP SEQUENCE IF EXISTS carrental.car_detail_seq_id;

CREATE SEQUENCE carrental.car_detail_seq_id;

CREATE TABLE carrental.car_detail (
                                "id"           BIGINT                   NOT NULL DEFAULT nextval('carrental.car_detail_seq_id'),
                                "car_id"       BIGINT                   NOT NULL REFERENCES carrental."car" (id),
                                "price"        DOUBLE PRECISION         NOT NULL ,
                                "status"       VARCHAR(50)              NOT NULL
                               );

ALTER TABLE carrental.car_detail
  ADD CONSTRAINT car_detail_id_pk PRIMARY KEY (id);

CREATE UNIQUE INDEX carrental_car_detail_seq_id
  ON carrental.car_detail (id);