CREATE TABLE cash_movement (
           id_movement BIGINT PRIMARY KEY AUTO_INCREMENT,
           type ENUM('SALE','WITHDRAWAL','DEPOSIT','REFUND','EXPENSE','PURCHASE','SALE_CANCELLED') NOT NULL,
           amount DECIMAL(10,2) NOT NULL,
           movement_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
           description VARCHAR(200) NOT NULL,
           user_id BIGINT NOT NULL,
           sale_id BIGINT NULL,
           cancelled_sale_id BIGINT NULL,
           cr_id BIGINT NOT NULL,

           CONSTRAINT fk_cash_movement_user
               FOREIGN KEY (user_id)
                   REFERENCES user(id_user),

           CONSTRAINT fk_cash_movement_sale
               FOREIGN KEY (sale_id)
                   REFERENCES sales(id_sale),

           CONSTRAINT fk_cash_movement_cancelled_sale
               FOREIGN KEY (cancelled_sale_id)
                   REFERENCES cancelled_sales(id_cancellation),

           CONSTRAINT fk_cash_movement_cash_register
               FOREIGN KEY (cr_id)
                   REFERENCES cash_register(id_cash_register)
);