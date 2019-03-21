DROP TABLE IF EXISTS carrental.user;

DROP SEQUENCE IF EXISTS carrental.user_seq_id;

CREATE SEQUENCE carrental.user_seq_id;

CREATE TABLE carrental.user (
                               "id"          BIGINT                   NOT NULL DEFAULT nextval('carrental.user_seq_id'),
                              "email" VARCHAR(254) NOT NULL PRIMARY KEY,
                               "passwordHash" VARCHAR(60) NOT NULL,
                               "token" VARCHAR(65) NULL,
                               "active" BOOL NOT NULL DEFAULT 0,
                               "deleted" BOOL NOT NULL DEFAULT 0
);

CREATE UNIQUE INDEX carrental_user_seq_id
  ON carrental.user (id);