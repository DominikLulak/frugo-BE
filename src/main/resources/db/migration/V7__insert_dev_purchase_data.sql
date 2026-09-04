INSERT INTO supplier (name, internal_code)
VALUES
    ('Fresh Food Supplier s.r.o.', 'SUP-001'),
    ('Global Food Distribution a.s.', 'SUP-002'),
    ('Central European Foods s.r.o.', 'SUP-003'),
    ('Premium Ingredients CZ s.r.o.', 'SUP-004');

INSERT INTO purchase_order (
    purchase_order_number,
    supplier_id,
    created_at,
    employee_id,
    status_id
)
VALUES
    (
        'PO-2026-0001',
        (SELECT id FROM supplier WHERE internal_code = 'SUP-001'),
        '2026-08-20 08:15:00',
        (SELECT MIN(id) FROM employee),
        (SELECT id FROM status WHERE code = 'COMPLETED')
    ),
    (
        'PO-2026-0002',
        (SELECT id FROM supplier WHERE internal_code = 'SUP-002'),
        '2026-08-22 10:30:00',
        (SELECT MIN(id) FROM employee),
        (SELECT id FROM status WHERE code = 'RELEASED')
    ),
    (
        'PO-2026-0003',
        (SELECT id FROM supplier WHERE internal_code = 'SUP-003'),
        '2026-08-25 14:20:00',
        (SELECT MIN(id) FROM employee),
        (SELECT id FROM status WHERE code = 'ENTERED')
    ),
    (
        'PO-2026-0004',
        (SELECT id FROM supplier WHERE internal_code = 'SUP-004'),
        '2026-08-28 09:45:00',
        (SELECT MIN(id) FROM employee),
        (SELECT id FROM status WHERE code = 'CANCELLED')
    );

INSERT INTO purchase_order_item (
    purchase_order_id,
    product_id,
    quantity,
    received_quantity,
    country_of_origin_id,
    status_id
)
VALUES

-- PO-2026-0001
-- vše kompletně přijato
(
    (SELECT id FROM purchase_order WHERE purchase_order_number = 'PO-2026-0001'),
    1,
    500,
    500,
    (SELECT id FROM country WHERE code = 'CZ'),
    (SELECT id FROM status WHERE code = 'COMPLETED')
),
(
    (SELECT id FROM purchase_order WHERE purchase_order_number = 'PO-2026-0001'),
    2,
    300,
    300,
    (SELECT id FROM country WHERE code = 'CZ'),
    (SELECT id FROM status WHERE code = 'COMPLETED')
),

-- PO-2026-0002
-- částečně přijato
(
    (SELECT id FROM purchase_order WHERE purchase_order_number = 'PO-2026-0002'),
    3,
    1000,
    750,
    (SELECT id FROM country WHERE code = 'CZ'),
    (SELECT id FROM status WHERE code = 'RELEASED')
),
(
    (SELECT id FROM purchase_order WHERE purchase_order_number = 'PO-2026-0002'),
    4,
    250,
    0,
    (SELECT id FROM country WHERE code = 'CZ'),
    (SELECT id FROM status WHERE code = 'RELEASED')
),

-- PO-2026-0003
-- nic zatím nepřijato
(
    (SELECT id FROM purchase_order WHERE purchase_order_number = 'PO-2026-0003'),
    1,
    200,
    0,
    (SELECT id FROM country WHERE code = 'CZ'),
    (SELECT id FROM status WHERE code = 'ENTERED')
),
(
    (SELECT id FROM purchase_order WHERE purchase_order_number = 'PO-2026-0003'),
    5,
    600,
    0,
    (SELECT id FROM country WHERE code = 'CZ'),
    (SELECT id FROM status WHERE code = 'ENTERED')
),

-- PO-2026-0004
-- zrušená objednávka
(
    (SELECT id FROM purchase_order WHERE purchase_order_number = 'PO-2026-0004'),
    2,
    400,
    0,
    (SELECT id FROM country WHERE code = 'CZ'),
    (SELECT id FROM status WHERE code = 'CANCELLED')
),
(
    (SELECT id FROM purchase_order WHERE purchase_order_number = 'PO-2026-0004'),
    4,
    800,
    0,
    (SELECT id FROM country WHERE code = 'CZ'),
    (SELECT id FROM status WHERE code = 'CANCELLED')
);