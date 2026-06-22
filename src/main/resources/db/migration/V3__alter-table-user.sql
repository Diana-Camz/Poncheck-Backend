ALTER TABLE user
    ADD CONSTRAINT fk_user_business
        FOREIGN KEY (business_id)
            REFERENCES business(id_business);