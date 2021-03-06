DROP TABLE IF EXISTS carrental.user;

DROP SEQUENCE IF EXISTS carrental.user_seq_id;

CREATE SEQUENCE carrental.user_seq_id;

CREATE TABLE carrental.user (
                              "id"          BIGINT                   NOT NULL DEFAULT nextval('carrental.user_seq_id'),
                              "role"        CHAR(1)                  NOT NULL,
                              "status"      CHAR(1)                  NOT NULL,
                              "mail"        VARCHAR(100)             NOT NULL,
                              "first_name"   VARCHAR(100)             NOT NULL,
                              "last_name"    VARCHAR(100)             NOT NULL,
                              "login"       TEXT                     NOT NULL,
                              "salt"        TEXT                     NOT NULL,
                              "password"    TEXT                     NOT NULL,
                              "created_at"   TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
                              "modified_at"  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
                              );

ALTER TABLE carrental.user
ADD CONSTRAINT user_id_pk PRIMARY KEY (id);

CREATE UNIQUE INDEX carrental_user_id_unique
ON carrental.user (id);
