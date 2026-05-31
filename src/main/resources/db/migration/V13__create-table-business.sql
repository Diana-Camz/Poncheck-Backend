CREATE TABLE business
(
    id_business   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name          varchar(255) NOT NULL,
    business_code varchar(255) NOT NULL UNIQUE,
    active        TINYINT(1)   NOT NULL DEFAULT 1,
    phone         varchar(255),
    email         varchar(255),
    address       varchar(255),
    description   varchar(255),
    logo_url      varchar(255),
    created_at    timestamp    NOT NULL DEFAULT (CURRENT_TIMESTAMP),
    owner_id      BIGINT
);

ALTER TABLE business ADD FOREIGN KEY (owner_id) REFERENCES user (id_user);