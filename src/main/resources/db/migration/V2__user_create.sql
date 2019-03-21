DROP TABLE IF EXISTS carrental.user;

CREATE TABLE carrental.user (
                               "email" VARCHAR(254) NOT NULL PRIMARY KEY,
                               "passwordHash" VARCHAR(60) NOT NULL,
                               "token" VARCHAR(65) NULL,
                               "active" BOOLEAN NOT NULL DEFAULT false,
                               "deleted" BOOLEAN NOT NULL DEFAULT false
);



