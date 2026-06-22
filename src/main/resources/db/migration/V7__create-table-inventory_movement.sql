CREATE TABLE inventory_movement (
            id_movement BIGINT PRIMARY KEY AUTO_INCREMENT,
            type ENUM('PURCHASE','PRODUCTION','SALE_CANCELLED','ADJUSTMENT_IN','SALE','GIFT','COURTESY','SAMPLING','WASTE','ADJUSTMENT_OUT') NOT NULL,
            quantity INT NOT NULL,
            movement_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
            description VARCHAR(200),
            user_id BIGINT NOT NULL,
            product_id BIGINT NOT NULL,
            sale_id BIGINT NULL,
            reference_movement_id BIGINT NULL,

            CONSTRAINT fk_inventory_movement_user
                FOREIGN KEY (user_id)
                    REFERENCES user(id_user),

            CONSTRAINT fk_inventory_movement_product
                FOREIGN KEY (product_id)
                    REFERENCES product(id_product),

            CONSTRAINT fk_inventory_movement_sale
                FOREIGN KEY (sale_id)
                    REFERENCES sales(id_sale),

            CONSTRAINT fk_inventory_movement_reference
                FOREIGN KEY (reference_movement_id)
                    REFERENCES inventory_movement(id_movement)
);