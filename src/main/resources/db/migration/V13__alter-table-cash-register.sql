ALTER TABLE cash_register DROP FOREIGN KEY fk_cash_register_business;
ALTER TABLE cash_register  DROP INDEX business_id;
ALTER TABLE cash_register ADD CONSTRAINT fk_cash_register_business FOREIGN KEY (business_id) REFERENCES business(id_business);