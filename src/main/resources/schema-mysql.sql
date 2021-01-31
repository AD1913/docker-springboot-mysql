CREATE TABLE IF NOT EXISTS products (
  id VARCHAR(100) NOT NULL,
  sku VARCHAR(10) NOT NULL,
  description VARCHAR(100),
  l1category VARCHAR(50),
  l2category VARCHAR(50),
  l3category VARCHAR(50),
  l4category VARCHAR(50),
  l5category VARCHAR(50),
  price DOUBLE,
  location VARCHAR(20),
  qty INT
);