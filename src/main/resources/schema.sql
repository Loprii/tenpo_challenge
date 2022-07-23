CREATE TABLE users
(
    id        serial PRIMARY KEY,
    username  VARCHAR(255),
    password  VARCHAR(255),
);

CREATE TABLE transaction_history
(
    id               serial PRIMARY KEY,
    description      VARCHAR(255),
    status           VARCHAR(255),
    date_created     VARCHAR(255)
);