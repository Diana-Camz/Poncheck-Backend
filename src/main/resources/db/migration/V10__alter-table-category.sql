ALTER TABLE category DROP INDEX name;
ALTER TABLE category ADD CONSTRAINT uk_category_name_business UNIQUE (name, business_id);