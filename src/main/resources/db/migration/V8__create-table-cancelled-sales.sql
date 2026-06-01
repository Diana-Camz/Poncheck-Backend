CREATE TABLE cancelled_sales (
         id_cancellation BIGINT PRIMARY KEY AUTO_INCREMENT,
         date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
         reason VARCHAR(255),
         user_id BIGINT NOT NULL,
         sale_id BIGINT NOT NULL UNIQUE,

         CONSTRAINT fk_cancelled_sales_user
             FOREIGN KEY (user_id)
                 REFERENCES user(id_user),

         CONSTRAINT fk_cancelled_sales_sale
             FOREIGN KEY (sale_id)
                 REFERENCES sales(id_sale)
);