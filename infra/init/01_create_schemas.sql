-- ======================================================
-- SCHEMA INITIALIZATION SCRIPT
-- Schema: plural form (users, orders, ...)
--   → avoids SQL reserved keywords like "user", "order"
-- ======================================================
CREATE SCHEMA IF NOT EXISTS ai           AUTHORIZATION luckyvicky;
CREATE SCHEMA IF NOT EXISTS companies    AUTHORIZATION luckyvicky;
CREATE SCHEMA IF NOT EXISTS deliveries   AUTHORIZATION luckyvicky;
CREATE SCHEMA IF NOT EXISTS hubs         AUTHORIZATION luckyvicky;
CREATE SCHEMA IF NOT EXISTS orders       AUTHORIZATION luckyvicky;
CREATE SCHEMA IF NOT EXISTS products     AUTHORIZATION luckyvicky;
CREATE SCHEMA IF NOT EXISTS routes       AUTHORIZATION luckyvicky;
CREATE SCHEMA IF NOT EXISTS slack        AUTHORIZATION luckyvicky;
CREATE SCHEMA IF NOT EXISTS users        AUTHORIZATION luckyvicky;