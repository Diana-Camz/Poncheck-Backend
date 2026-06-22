ALTER TABLE cancelled_sales
    ADD COLUMN business_id BIGINT NOT NULL,
ADD CONSTRAINT fk_cancelled_sales_business
FOREIGN KEY (business_id)
REFERENCES business(id_business);