CREATE TABLE cl_user
(
    id BIGINT PRIMARY KEY NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    settings VARCHAR(5000) NOT NULL,
    avatar VARCHAR(5000),
    mobile_token VARCHAR(5000) NOT NULL,
    fb_id BIGINT NOT NULL,
    set BYTEA
);
CREATE UNIQUE INDEX cl_user_id_uindex ON cl_user (id);
CREATE UNIQUE INDEX cl_user_mobile_token_uindex ON cl_user (mobile_token);