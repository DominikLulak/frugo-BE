-- ============================================================
-- Product stock thresholds
-- ============================================================

ALTER TABLE product
    ADD COLUMN reorder_point INTEGER NOT NULL DEFAULT 0,
    ADD COLUMN minimum_stock INTEGER NOT NULL DEFAULT 0;

ALTER TABLE product
    ADD CONSTRAINT chk_product_stock_levels
        CHECK (
            minimum_stock >= 0
                AND reorder_point >= minimum_stock
            );


-- ============================================================
-- Test values for existing products
-- ============================================================

UPDATE product
SET
    reorder_point = 50,
    minimum_stock = 20
WHERE id IN (1, 2, 3, 4, 5);