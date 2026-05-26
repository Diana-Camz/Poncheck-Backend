CREATE TABLE sales (
   id_sale BIGINT PRIMARY KEY AUTO_INCREMENT,
   total decimal(10,2) NOT NULL,
   payment_method enum('cash','card','transfer') NOT NULL,
   date timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP),
   description VARCHAR(255),
   cancelled TINYINT(1) NOT NULL DEFAULT 0,
   user_id BIGINT NOT NULL
);

CREATE TABLE products_sale (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    sale_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity int NOT NULL DEFAULT 1,
    unit_price decimal(10,2) NOT NULL,
    subtotal decimal(10,2) NOT NULL
);

ALTER TABLE sales ADD FOREIGN KEY (user_id) REFERENCES user (id_user);
ALTER TABLE products_sale ADD FOREIGN KEY (sale_id) REFERENCES sales (id_sale);
ALTER TABLE products_sale ADD FOREIGN KEY (product_id) REFERENCES product (id_product);