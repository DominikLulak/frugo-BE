-- ============================================================
-- Event definitions
-- ============================================================

INSERT INTO event_definition
(id, code, name, description)
VALUES
    (1, 'STOCK_RECEIVED', 'Stock received',
     'Goods were received into the warehouse'),

    (2, 'STOCK_MOVED', 'Stock moved',
     'Warehouse item was moved to another location'),

    (3, 'STOCK_ALLOCATED', 'Stock allocated',
     'Stock was allocated to an order'),

    (4, 'STOCK_PICKED', 'Stock picked',
     'Stock was picked for an order'),

    (5, 'SHIPMENT_CREATED', 'Shipment created',
     'Shipment was created for an order'),

    (6, 'SHIPMENT_COMPLETED', 'Shipment completed',
     'Shipment was completed');


-- ============================================================
-- ETI sequences
-- ============================================================

INSERT INTO eti_sequence
(id, description, code, last_number)
VALUES
    (1, 'Warehouse item ETI numbers', 'WAREHOUSE_ITEM', 5),

    (2, 'Pallet ETI numbers', 'PALLET', 2);


-- ============================================================
-- Stock movements
-- ============================================================

INSERT INTO stock_movement
(
    id,
    event_definition_id,
    created_at,
    purchase_order_id,
    warehouse_item_id,
    new_warehouse_item_id,
    quantity,
    from_location_id,
    to_location_id,
    employee_id,
    pallet_id,
    order_id,
    shipment_id
)
VALUES

    -- Item 1 was received into location A-01-01-01
    (
        1,
        1,
        '2026-08-20 08:00:00',
        NULL,
        1,
        NULL,
        100,
        NULL,
        1,
        1,
        NULL,
        NULL,
        NULL
    ),

    -- Item 2 was received into location A-01-01-02
    (
        2,
        1,
        '2026-08-22 09:30:00',
        NULL,
        2,
        NULL,
        80,
        NULL,
        2,
        2,
        NULL,
        NULL,
        NULL
    ),

    -- Item 3 was received into the picking area
    (
        3,
        1,
        '2026-08-24 10:00:00',
        NULL,
        3,
        NULL,
        200,
        NULL,
        3,
        1,
        NULL,
        NULL,
        NULL
    ),

    -- 20 kg of item 1 was allocated to order 1
    (
        4,
        3,
        '2026-08-27 09:20:00',
        NULL,
        1,
        NULL,
        20,
        1,
        1,
        1,
        NULL,
        1,
        NULL
    ),

    -- 10 kg of item 2 was allocated to order 1
    (
        5,
        3,
        '2026-08-27 09:25:00',
        NULL,
        2,
        NULL,
        10,
        2,
        2,
        1,
        NULL,
        1,
        NULL
    ),

    -- 5 kg of item 2 was picked for order 1
    (
        6,
        4,
        '2026-08-27 10:00:00',
        NULL,
        2,
        NULL,
        5,
        2,
        NULL,
        1,
        NULL,
        1,
        NULL
    ),

    -- Shipment 1 was created
    (
        7,
        5,
        '2026-08-27 10:30:00',
        NULL,
        1,
        NULL,
        20,
        NULL,
        NULL,
        1,
        1,
        1,
        1
    );


-- ============================================================
-- Event log
-- ============================================================

INSERT INTO event_log
(
    id,
    event_definition_id,
    created_at,
    employee_id,
    description,
    data
)
VALUES

    (
        1,
        1,
        '2026-08-20 08:00:00',
        1,
        'Red Apple stock received',
        '{
            "warehouseItemId": 1,
            "etiNumber": "ETI-000001",
            "quantity": 100,
            "locationId": 1
        }'::jsonb
    ),

    (
        2,
        1,
        '2026-08-22 09:30:00',
        2,
        'Banana stock received',
        '{
            "warehouseItemId": 2,
            "etiNumber": "ETI-000002",
            "quantity": 80,
            "locationId": 2
        }'::jsonb
    ),

    (
        3,
        1,
        '2026-08-24 10:00:00',
        1,
        'Carrot stock received',
        '{
            "warehouseItemId": 3,
            "etiNumber": "ETI-000003",
            "quantity": 200,
            "locationId": 3
        }'::jsonb
    ),

    (
        4,
        3,
        '2026-08-27 09:20:00',
        1,
        'Stock allocated to order ORD-2026-0001',
        '{
            "warehouseItemId": 1,
            "orderId": 1,
            "quantity": 20
        }'::jsonb
    ),

    (
        5,
        3,
        '2026-08-27 09:25:00',
        1,
        'Stock allocated to order ORD-2026-0001',
        '{
            "warehouseItemId": 2,
            "orderId": 1,
            "quantity": 10
        }'::jsonb
    ),

    (
        6,
        4,
        '2026-08-27 10:00:00',
        1,
        'Stock picked for order ORD-2026-0001',
        '{
            "warehouseItemId": 2,
            "orderId": 1,
            "quantity": 5
        }'::jsonb
    ),

    (
        7,
        5,
        '2026-08-27 10:30:00',
        1,
        'Shipment SHP-2026-0001 created',
        '{
            "shipmentId": 1,
            "orderId": 1,
            "palletId": 1
        }'::jsonb
    );


-- ============================================================
-- Event log entities
-- ============================================================

INSERT INTO event_log_entity
(id, event_log_id, entity_type, entity_id)
VALUES

    -- Stock received
    (1, 1, 'WAREHOUSE_ITEM', 1),
    (2, 2, 'WAREHOUSE_ITEM', 2),
    (3, 3, 'WAREHOUSE_ITEM', 3),

    -- Stock allocated
    (4, 4, 'WAREHOUSE_ITEM', 1),
    (5, 4, 'ORDER', 1),

    (6, 5, 'WAREHOUSE_ITEM', 2),
    (7, 5, 'ORDER', 1),

    -- Stock picked
    (8, 6, 'WAREHOUSE_ITEM', 2),
    (9, 6, 'ORDER', 1),

    -- Shipment created
    (10, 7, 'SHIPMENT', 1),
    (11, 7, 'ORDER', 1),
    (12, 7, 'PALLET', 1),
    (13, 7, 'EMPLOYEE', 1);


-- ============================================================
-- Reset PostgreSQL sequences
-- ============================================================

SELECT setval(
               pg_get_serial_sequence('event_definition', 'id'),
               COALESCE((SELECT MAX(id) FROM event_definition), 1),
               true
       );

SELECT setval(
               pg_get_serial_sequence('eti_sequence', 'id'),
               COALESCE((SELECT MAX(id) FROM eti_sequence), 1),
               true
       );

SELECT setval(
               pg_get_serial_sequence('stock_movement', 'id'),
               COALESCE((SELECT MAX(id) FROM stock_movement), 1),
               true
       );

SELECT setval(
               pg_get_serial_sequence('event_log', 'id'),
               COALESCE((SELECT MAX(id) FROM event_log), 1),
               true
       );

SELECT setval(
               pg_get_serial_sequence('event_log_entity', 'id'),
               COALESCE((SELECT MAX(id) FROM event_log_entity), 1),
               true
       );