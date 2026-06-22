CREATE TABLE cash_register (
           id_cash_register BIGINT PRIMARY KEY AUTO_INCREMENT,
           opening_amount DECIMAL(10,2) NOT NULL,
           expected_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00,
           real_amount DECIMAL(10,2),
           difference DECIMAL(10,2) NOT NULL DEFAULT 0.00,
           opened_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
           closed_at TIMESTAMP NULL,
           description VARCHAR(255),
           status ENUM('CLOSED', 'OPEN') NOT NULL,
           opened_by BIGINT NOT NULL,
           closed_by BIGINT,
           business_id BIGINT NOT NULL UNIQUE,

           CONSTRAINT fk_cash_register_opened_by
               FOREIGN KEY (opened_by)
                   REFERENCES user(id_user),

           CONSTRAINT fk_cash_register_closed_by
               FOREIGN KEY (closed_by)
                   REFERENCES user(id_user),

           CONSTRAINT fk_cash_register_business
               FOREIGN KEY (business_id)
                   REFERENCES business(id_business)
);