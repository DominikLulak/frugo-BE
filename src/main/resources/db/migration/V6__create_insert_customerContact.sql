ALTER TABLE customer_contact
    ADD COLUMN is_primary BOOLEAN NOT NULL DEFAULT FALSE;


INSERT INTO customer_contact (
    customer_id,
    name,
    phone_number,
    email,
    is_primary
)
VALUES
    (
        1,
        'Petr Svoboda',
        '+420 777 111 111',
        'petr.svoboda@example.com',
        TRUE
    ),
    (
        2,
        'Lucie Dvořáková',
        '+420 777 222 222',
        'lucie.dvorakova@example.com',
        TRUE
    ),
    (
        3,
        'Jan Novák',
        '+420 777 333 333',
        'jan.novak@example.com',
        TRUE
    );