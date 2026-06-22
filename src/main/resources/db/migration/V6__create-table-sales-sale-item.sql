CREATE TABLE sales (
       id_sale BIGINT PRIMARY KEY AUTO_INCREMENT,
       total DECIMAL(10,2) NOT NULL,
       payment_method ENUM('CASH','CARD','TRANSFER') NOT NULL,
       date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
       description VARCHAR(255),
       sale_status ENUM('PENDING','COMPLETED','CANCELLED','REFUNDED') NOT NULL,
       user_id BIGINT NOT NULL,
       cr_id BIGINT NOT NULL,
       business_id BIGINT NOT NULL,

       CONSTRAINT fk_sales_user
           FOREIGN KEY (user_id)
               REFERENCES user(id_user),

       CONSTRAINT fk_sales_cash_register
           FOREIGN KEY (cr_id)
               REFERENCES cash_register(id_cash_register),

       CONSTRAINT fk_sales_business
           FOREIGN KEY (business_id)
               REFERENCES business(id_business)
);

CREATE TABLE sale_item (
       id BIGINT PRIMARY KEY AUTO_INCREMENT,
       quantity INT NOT NULL,
       unit_price DECIMAL(10,2) NOT NULL,
       subtotal DECIMAL(10,2) NOT NULL,
       product_id BIGINT NOT NULL,
       sale_id BIGINT NOT NULL,

       CONSTRAINT fk_sale_item_product
           FOREIGN KEY (product_id)
               REFERENCES product(id_product),

       CONSTRAINT fk_sale_item_sale
           FOREIGN KEY (sale_id)
               REFERENCES sales(id_sale)
);