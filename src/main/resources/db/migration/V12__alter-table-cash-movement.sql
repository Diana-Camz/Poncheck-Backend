ALTER TABLE cash_movement
    MODIFY COLUMN type ENUM(
    'SALE',
    'WITHDRAWAL',
    'DEPOSIT',
    'REFUND',
    'EXPENSE',
    'PURCHASE',
    'SALE_CANCELLED'
    ) NOT NULL;