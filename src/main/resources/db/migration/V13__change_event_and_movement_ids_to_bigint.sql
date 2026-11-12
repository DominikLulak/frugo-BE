
-- ==========================================
-- V10 - Change event and movement IDs
--       from INTEGER to BIGINT
-- ==========================================

-- 1. Event log
ALTER TABLE event_log
ALTER COLUMN id TYPE BIGINT;

-- 2. Event log steps
ALTER TABLE event_log_step
ALTER COLUMN id TYPE BIGINT,
    ALTER COLUMN event_log_id TYPE BIGINT;

-- 3. Event log entities
ALTER TABLE event_log_entity
ALTER COLUMN id TYPE BIGINT,
    ALTER COLUMN event_log_id TYPE BIGINT;

-- 4. Stock movements
ALTER TABLE stock_movement
ALTER COLUMN id TYPE BIGINT;
