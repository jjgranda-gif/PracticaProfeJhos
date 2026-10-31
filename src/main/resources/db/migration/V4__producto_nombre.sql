ALTER TABLE product ADD COLUMN IF NOT EXISTS name VARCHAR(100);

UPDATE product SET name = description WHERE name IS NULL AND description IS NOT NULL;
UPDATE product SET name = 'Producto ' || id WHERE name IS NULL;

ALTER TABLE product ALTER COLUMN name SET NOT NULL;

UPDATE product SET stock = 0 WHERE stock IS NULL;

ALTER TABLE product DROP COLUMN IF EXISTS price;