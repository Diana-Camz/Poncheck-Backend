ALTER TABLE cash_movement
    ADD COLUMN business_id BIGINT NOT NULL,
ADD CONSTRAINT fk_cash_movement_business
FOREIGN KEY (business_id)
REFERENCES business(id_business);

ALTER TABLE inventory_movement
    ADD COLUMN business_id BIGINT NOT NULL,
ADD CONSTRAINT fk_inventory_movement_business
FOREIGN KEY (business_id)
REFERENCES business(id_business);