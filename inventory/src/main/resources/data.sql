-- Insert Products
INSERT INTO product (id, name) VALUES (1, 'Laptop');
INSERT INTO product (id, name) VALUES (2, 'Smartphone');

-- Insert Inventory Batches
INSERT INTO inventory_batch (id, product_id, quantity, expiry_date) VALUES (1, 1, 10, '2025-12-31');
INSERT INTO inventory_batch (id, product_id, quantity, expiry_date) VALUES (2, 1, 5, '2025-11-30');
INSERT INTO inventory_batch (id, product_id, quantity, expiry_date) VALUES (3, 2, 20, '2026-01-15');