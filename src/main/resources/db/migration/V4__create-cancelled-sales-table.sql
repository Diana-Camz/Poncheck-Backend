CREATE TABLE cancelled_sales (
                       id_cancelation BIGINT PRIMARY KEY AUTO_INCREMENT,
                       date timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP),
                       reason VARCHAR(255),
                       user_id BIGINT NOT NULL,
                       sale_id BIGINT NOT NULL
);

ALTER TABLE cancelled_sales ADD FOREIGN KEY (user_id) REFERENCES user (id_user);
ALTER TABLE cancelled_sales ADD FOREIGN KEY (sale_id) REFERENCES sales (id_sale);