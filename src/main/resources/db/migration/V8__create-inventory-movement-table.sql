CREATE TABLE inventory_movement (
                    id_movement BIGINT PRIMARY KEY AUTO_INCREMENT,
                    reference_movement_id BIGINT,
                    type enum('SALE','PURCHASE','PRODUCTION', 'GIFT', 'COURTESY', 'SAMPLING', 'EXCHANGE', 'WASTE', 'SALE_CANCELLED') NOT NULL,
                    quantity int NOT NULL CHECK (quantity > 0),
                    movement_at timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP),
                    description VARCHAR(255),
                    user_id BIGINT NOT NULL,
                    product_id BIGINT NOT NULL,
                    sale_id BIGINT
);

ALTER TABLE inventory_movement ADD FOREIGN KEY (reference_movement_id) REFERENCES inventory_movement (id_movement);
ALTER TABLE inventory_movement ADD FOREIGN KEY (user_id) REFERENCES user (id_user);
ALTER TABLE inventory_movement ADD FOREIGN KEY (product_id) REFERENCES product (id_product);
ALTER TAble inventory_movement ADD FOREIGN KEY (sale_id) REFERENCES sales (id_sale);