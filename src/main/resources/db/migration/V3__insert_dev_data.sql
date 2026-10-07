-- DEV / TEST DATA ONLY
-- This script is intended for the development database.
-- Do NOT use these values as production data.

BEGIN;

-- ============================================================
-- Reference data
-- ============================================================

INSERT INTO category (id, name, code) VALUES
    (1, 'Fruit', 'FRT'),
    (2, 'Vegetables', 'VEG'),
    (3, 'Herbs', 'HER');

INSERT INTO packaging (id, code, name, description) VALUES
    (1, 'BOX', 'Box', 'Cardboard box'),
    (2, 'CRATE', 'Crate', 'Reusable plastic crate'),
    (3, 'BAG', 'Bag', 'Plastic bag');

INSERT INTO country (id, code, name) VALUES
    (1, 'CZ', 'Czech Republic'),
    (2, 'SK', 'Slovakia'),
    (3, 'PL', 'Poland'),
    (4, 'NL', 'Netherlands');

INSERT INTO status (id, code, name, description) VALUES
    (1, 'ENTERED', 'Entered', 'Newly created record'),
    (2, 'RELEASED', 'Released', 'Ready  to process'),
    (3, 'BLOCKED', 'Blocked', 'Task is blocked'),
    (4, 'COMPLETED', 'Completed', 'Successfully completed'),
    (5, 'CANCELLED', 'Cancelled', 'Cancelled record'),
    (6, 'FINISHED', 'Finished', 'Finished by expedition');

INSERT INTO shift (id, code, description) VALUES
    (1, 'MORNING', 'Morning shift'),
    (2, 'AFTERNOON', 'Afternoon shift'),
    (3, 'NIGHT', 'Night shift');

-- ============================================================
-- Warehouse structure
-- ============================================================

INSERT INTO warehouse (id, code, name, description) VALUES
    (1, 'WH1', 'Main Warehouse', 'Main Frugo warehouse'),
    (2, 'WH2', 'Cold Warehouse', 'Temperature-controlled warehouse');

INSERT INTO sector_type (id, code, name, description) VALUES
    (1, 'STORAGE', 'Storage', 'Standard storage sector'),
    (2, 'PICKING', 'Picking', 'Sector used for order picking'),
    (3, 'COLD', 'Cold Storage', 'Temperature-controlled sector');

INSERT INTO sector (id, warehouse_id, code, name, type_id, description) VALUES
    (1, 1, 'A', 'Aisle A', 1, 'Standard storage aisle'),
    (2, 1, 'P', 'Picking Area', 2, 'Main picking area'),
    (3, 2, 'C', 'Cold Area', 3, 'Cold storage area');

INSERT INTO location
    (id, sector_id, code, aisle, rack, level, position, can_be_ordered)
VALUES
    (1, 1, 'A-01-01-01', 1, 1, 1, 1, false),
    (2, 1, 'A-01-01-02', 1, 1, 1, 2, false),
    (3, 2, 'P-01-01-01', 1, 1, 1, 1, true),
    (4, 2, 'P-01-01-02', 1, 1, 1, 2, true),
    (5, 3, 'C-01-01-01', 1, 1, 1, 1, true);

-- ============================================================
-- Product catalogue
-- ============================================================

INSERT INTO product_type (id, category_id, name) VALUES
    (1, 1, 'Apple'),
    (2, 1, 'Banana'),
    (3, 2, 'Carrot'),
    (4, 2, 'Tomato'),
    (5, 3, 'Parsley');

INSERT INTO product
    (id, product_type_id, product_code, name, price_per_unit, shelf_life_days, is_for_sale)
VALUES
    (1, 1, 'APL-001', 'Red Apple', 39.90, 30, true),
    (2, 2, 'BAN-001', 'Banana', 34.90, 14, true),
    (3, 3, 'CAR-001', 'Carrot', 24.90, 21, true),
    (4, 4, 'TOM-001', 'Tomato', 49.90, 14, true),
    (5, 5, 'PAR-001', 'Parsley', 32.90, 10, true);

-- ============================================================
-- Customers
-- ============================================================

INSERT INTO customer
    (id, name, company_id, street, house_number, city, postal_code, country_id, is_registered)
VALUES
    (1, 'Fresh Market s.r.o.', 'CZ12345678', 'Hlavni', '15', 'Brno', '60200', 1, true),
    (2, 'Green Shop s.r.o.', 'CZ87654321', 'Javorova', '28', 'Ostrava', '70200', 1, true),
    (3, 'Jan Novak', NULL, 'Lipova', '7', 'Olomouc', '77900', 1, false);

-- ============================================================
-- Employees / RBAC
-- ============================================================

INSERT INTO department (id, name, code, description) VALUES
    (1, 'Warehouse', 'WH', 'Warehouse operations'),
    (2, 'Administration', 'ADM', 'Administrative staff');

INSERT INTO job_position (id, department_id, code, name, description) VALUES
    (1, 1, 'WH_WORKER', 'Warehouse Worker', 'Warehouse operator'),
    (2, 1, 'WH_MANAGER', 'Warehouse Manager', 'Warehouse supervisor'),
    (3, 2, 'ADMIN', 'Administrator', 'System administrator');

INSERT INTO employee
    (id, first_name, last_name, employee_number, address, city, postal_code,
     shift_id, birth_date, hire_date, phone, email, is_active,
     termination_date, job_position_id, system_username)
VALUES
    (1, 'Jan', 'Novak', 'EMP001', 'Kvetna 12', 'Brno', '60200',
     1, '1990-04-15', '2022-01-10', '+420700000001',
     'jan.novak@example.local', true, NULL, 3, 'CARD-0001'),

    (2, 'Petr', 'Svoboda', 'EMP002', 'Jiraskova 8', 'Brno', '60200',
     2, '1988-09-21', '2023-03-01', '+420700000002',
     'petr.svoboda@example.local', true, NULL, 1, 'CARD-0002'),

    (3, 'Lucie', 'Dvorakova', 'EMP003', 'Masarykova 21', 'Ostrava', '70200',
     1, '1995-12-03', '2024-06-17', '+420700000003',
     'lucie.dvorakova@example.local', true, NULL, 2, 'CARD-0003');

-- BCrypt hash for the development password "password".
INSERT INTO employee_login (employee_id, username, password_hash) VALUES
    (1, 'admin',    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'),
    (2, 'skladnik', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy');

INSERT INTO module (id, code, name) VALUES
    (1, 'CUSTOMERS', 'Customers'),
    (2, 'EMPLOYEES', 'Employees'),
    (3, 'PRODUCTS', 'Products'),
    (4, 'WAREHOUSE', 'Warehouse'),
    (5, 'ORDERS', 'Orders'),
    (6, 'SHIPMENTS', 'Shipments');

INSERT INTO role (id, code, name) VALUES
    (1, 'ADMIN', 'Administrator'),
    (2, 'USER', 'User');

INSERT INTO permission (id, module_id, code, name, description) VALUES
    (1, 1, 'CUSTOMER_READ', 'Read customers', 'View customer data'),
    (2, 2, 'EMPLOYEE_READ', 'Read employees', 'View employee data'),
    (3, 3, 'PRODUCT_READ', 'Read products', 'View product data'),
    (4, 4, 'WAREHOUSE_READ', 'Read warehouse', 'View warehouse data'),
    (5, 5, 'ORDER_READ', 'Read orders', 'View order data'),
    (6, 6, 'SHIPMENT_READ', 'Read shipments', 'View shipment data'),
    (7, 2, 'EMPLOYEE_MANAGE', 'Manage employees', 'Create and modify employees'),
    (8, 3, 'PRODUCT_MANAGE', 'Manage products', 'Create and modify products');

INSERT INTO employee_role (employee_id, role_id) VALUES
    (1, 1),
    (2, 2),
    (3, 2);

INSERT INTO role_permission (role_id, permission_id) VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 4),
    (1, 5),
    (1, 6),
    (1, 7),
    (1, 8),
    (2, 3),
    (2, 4),
    (2, 5),
    (2, 6);

-- ============================================================
-- Warehouse items
-- ============================================================

INSERT INTO warehouse_item
    (id, product_id, eti_number, packaging_id, quantity_per_package,
     subpackaging_id, quantity_per_subpackage, location_id, quantity,
     country_of_origin_id, received_at, expiration_date, allocated_quantity)
VALUES
    (1, 1, 'ETI-000001', 2, 10, NULL, NULL, 1, 100, 1,
     '2026-08-20 08:00:00', '2026-09-19', 20),

    (2, 2, 'ETI-000002', 2, 10, NULL, NULL, 2, 80, 2,
     '2026-08-22 09:30:00', '2026-09-05', 10),

    (3, 3, 'ETI-000003', 1, 20, 3, 5, 3, 200, 1,
     '2026-08-24 10:00:00', '2026-09-14', 30),

    (4, 4, 'ETI-000004', 2, 10, NULL, NULL, 4, 60, 4,
     '2026-08-25 11:15:00', '2026-09-08', 15),

    (5, 5, 'ETI-000005', 1, 12, NULL, NULL, 5, 48, 1,
     '2026-08-27 06:45:00', '2026-09-06', 12);

-- ============================================================
-- Orders
-- ============================================================

INSERT INTO orders (id, order_number, customer_id, created_at, status_id) VALUES
    (1, 'ORD-2026-0001', 1, '2026-08-27 09:15:00', 2),
    (2, 'ORD-2026-0002', 2, '2026-08-28 13:40:00', 1),
    (3, 'ORD-2026-0003', 3, '2026-08-28 16:20:00', 3);

INSERT INTO order_items
    (id, order_id, warehouse_item_id, quantity, picked_quantity, status_id)
VALUES
    (1, 1, 1, 20, 20, 4),
    (2, 1, 2, 10, 5, 2),
    (3, 2, 3, 30, 0, 1),
    (4, 3, 4, 10, 10, 3);

-- ============================================================
-- Pallets
-- ============================================================

INSERT INTO pallet (id, eti_number, location_id, is_closed) VALUES
    (1, 'PAL-000001', 3, false),
    (2, 'PAL-000002', 4, true);

INSERT INTO pallet_warehouse_item (pallet_id, warehouse_item_id) VALUES
    (1, 3),
    (2, 4);

-- ============================================================
-- Shipments
-- ============================================================

INSERT INTO shipment (id, shipment_number, order_id, status_id) VALUES
    (1, 'SHP-2026-0001', 1, 3),
    (2, 'SHP-2026-0002', 2, 1);

INSERT INTO shipment_item (id, shipment_id, pallet_id, status_id) VALUES
    (1, 1, 1, 3),
    (2, 2, 2, 1);

-- ============================================================
-- Reset PostgreSQL sequences after explicit IDs.
-- This is important because the seed uses explicit primary keys.
-- ============================================================

SELECT setval(pg_get_serial_sequence('category', 'id'),
              COALESCE((SELECT MAX(id) FROM category), 1), true);

SELECT setval(pg_get_serial_sequence('packaging', 'id'),
              COALESCE((SELECT MAX(id) FROM packaging), 1), true);

SELECT setval(pg_get_serial_sequence('country', 'id'),
              COALESCE((SELECT MAX(id) FROM country), 1), true);

SELECT setval(pg_get_serial_sequence('status', 'id'),
              COALESCE((SELECT MAX(id) FROM status), 1), true);

SELECT setval(pg_get_serial_sequence('shift', 'id'),
              COALESCE((SELECT MAX(id) FROM shift), 1), true);

SELECT setval(pg_get_serial_sequence('warehouse', 'id'),
              COALESCE((SELECT MAX(id) FROM warehouse), 1), true);

SELECT setval(pg_get_serial_sequence('sector_type', 'id'),
              COALESCE((SELECT MAX(id) FROM sector_type), 1), true);

SELECT setval(pg_get_serial_sequence('sector', 'id'),
              COALESCE((SELECT MAX(id) FROM sector), 1), true);

SELECT setval(pg_get_serial_sequence('location', 'id'),
              COALESCE((SELECT MAX(id) FROM location), 1), true);

SELECT setval(pg_get_serial_sequence('product_type', 'id'),
              COALESCE((SELECT MAX(id) FROM product_type), 1), true);

SELECT setval(pg_get_serial_sequence('product', 'id'),
              COALESCE((SELECT MAX(id) FROM product), 1), true);

SELECT setval(pg_get_serial_sequence('customer', 'id'),
              COALESCE((SELECT MAX(id) FROM customer), 1), true);

SELECT setval(pg_get_serial_sequence('department', 'id'),
              COALESCE((SELECT MAX(id) FROM department), 1), true);

SELECT setval(pg_get_serial_sequence('job_position', 'id'),
              COALESCE((SELECT MAX(id) FROM job_position), 1), true);

SELECT setval(pg_get_serial_sequence('employee', 'id'),
              COALESCE((SELECT MAX(id) FROM employee), 1), true);

SELECT setval(pg_get_serial_sequence('module', 'id'),
              COALESCE((SELECT MAX(id) FROM module), 1), true);

SELECT setval(pg_get_serial_sequence('role', 'id'),
              COALESCE((SELECT MAX(id) FROM role), 1), true);

SELECT setval(pg_get_serial_sequence('permission', 'id'),
              COALESCE((SELECT MAX(id) FROM permission), 1), true);

SELECT setval(pg_get_serial_sequence('warehouse_item', 'id'),
              COALESCE((SELECT MAX(id) FROM warehouse_item), 1), true);

SELECT setval(pg_get_serial_sequence('orders', 'id'),
              COALESCE((SELECT MAX(id) FROM orders), 1), true);

SELECT setval(pg_get_serial_sequence('order_items', 'id'),
              COALESCE((SELECT MAX(id) FROM order_items), 1), true);

SELECT setval(pg_get_serial_sequence('pallet', 'id'),
              COALESCE((SELECT MAX(id) FROM pallet), 1), true);

SELECT setval(pg_get_serial_sequence('shipment', 'id'),
              COALESCE((SELECT MAX(id) FROM shipment), 1), true);

SELECT setval(pg_get_serial_sequence('shipment_item', 'id'),
              COALESCE((SELECT MAX(id) FROM shipment_item), 1), true);

COMMIT;
