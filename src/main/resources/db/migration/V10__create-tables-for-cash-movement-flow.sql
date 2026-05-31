CREATE TABLE cash_register
(
    id_cr           BIGINT PRIMARY KEY AUTO_INCREMENT,
    opened_by       BIGINT         NOT NULL,
    closed_by       BIGINT,
    opening_amount  decimal(10, 2) NOT NULL,
    expected_amount decimal(10, 2),
    real_amount     decimal(10, 2),
    difference      decimal(10, 2),
    opened_at       timestamp      NOT NULL DEFAULT (CURRENT_TIMESTAMP),
    closed_at       timestamp,
    description     varchar(255),
    status          enum('CLOSED', 'OPEN') NOT NULL
);

ALTER TABLE cash_register ADD FOREIGN KEY (opened_by) REFERENCES user (id_user);
ALTER TABLE cash_register ADD FOREIGN KEY (closed_by) REFERENCES user (id_user);


CREATE TABLE cash_movement
(
    id_movement       BIGINT PRIMARY KEY AUTO_INCREMENT,
    type              enum('SALE', 'DEPOSIT', 'REFUND', 'WITHDRAWAL', 'EXPENSE') NOT NULL,
    amount            decimal(10, 2) NOT NULL,
    description       VARCHAR(255),
    movement_at       timestamp      NOT NULL DEFAULT (CURRENT_TIMESTAMP),
    user_id           BIGINT         NOT NULL,
    sale_id           BIGINT,
    cancelled_sale_id BIGINT,
    cr_id             BIGINT         NOT NULL
);

ALTER TABLE cash_movement ADD FOREIGN KEY (user_id) REFERENCES user (id_user);
ALTER TABLE cash_movement ADD FOREIGN KEY (sale_id) REFERENCES sales (id_sale);
ALTER TABLE cash_movement ADD FOREIGN KEY (cancelled_sale_id) REFERENCES cancelled_sales (id_cancelation);
ALTER TABLE cash_movement ADD FOREIGN KEY (cr_id) REFERENCES cash_register (id_cr);

ALTER TABLE sales ADD COLUMN cr_id BIGINT;
ALTER TABLE sales ADD FOREIGN KEY (cr_id) REFERENCES cash_register (id_cr);
