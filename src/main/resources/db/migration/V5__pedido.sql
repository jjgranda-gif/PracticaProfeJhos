CREATE TABLE IF NOT EXISTS pedido (
    id                SERIAL PRIMARY KEY,
    customer_name     VARCHAR(100) NOT NULL,
    product_names     TEXT[]       NOT NULL,
    quantities        INTEGER[]    NOT NULL,
    status            VARCHAR(20)  NOT NULL DEFAULT 'PENDIENTE',
    verification_code VARCHAR(6)
    );