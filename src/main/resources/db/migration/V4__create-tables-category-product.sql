CREATE TABLE category
(
    id_category BIGINT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(100) NOT NULL UNIQUE,
    active      TINYINT NOT NULL DEFAULT 1,
    business_id BIGINT       NOT NULL,

    CONSTRAINT fk_category_business
        FOREIGN KEY (business_id)
            REFERENCES business (id_business)
);

CREATE TABLE product
(
    id_product   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(100)   NOT NULL,
    code         VARCHAR(100)   NOT NULL,
    stock        INT            NOT NULL,
    price        DECIMAL(10, 2) NOT NULL,
    flavor       VARCHAR(50),
    description  VARCHAR(100),
    active       TINYINT NOT NULL DEFAULT 1,
    ponche_base  ENUM('WATER','MILK','WINE','MEZCAL'),
    product_size ENUM('SMALL','MEDIUM','LARGE'),
    category_id  BIGINT         NOT NULL,
    business_id  BIGINT         NOT NULL,

    CONSTRAINT uk_product_business_code UNIQUE (business_id, code),

    CONSTRAINT fk_product_category
        FOREIGN KEY (category_id)
            REFERENCES category (id_category),

    CONSTRAINT fk_product_business
        FOREIGN KEY (business_id)
            REFERENCES business (id_business)
);