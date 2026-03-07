-- Users table
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    phone VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Categories table
CREATE TABLE IF NOT EXISTS categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    type VARCHAR(20) NOT NULL, -- 'INCOME' or 'EXPENSE'
    user_id BIGINT, -- NULL for system default, otherwise user specific
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Transactions table
CREATE TABLE IF NOT EXISTS transactions (
    id BIGSERIAL PRIMARY KEY,
    amount DECIMAL(15, 2) NOT NULL,
    type VARCHAR(20) NOT NULL, -- 'INCOME' or 'EXPENSE'
    category_id BIGINT,
    user_id BIGINT NOT NULL,
    description VARCHAR(255),
    transaction_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- Insert default user (ID 1)
INSERT INTO users (id, phone, password) VALUES (1, '13800138000', 'password123') ON CONFLICT (id) DO NOTHING;

-- Insert default categories if not exists
INSERT INTO categories (id, name, type, user_id) VALUES 
(1, 'Salary', 'INCOME', NULL),
(2, 'Food', 'EXPENSE', NULL),
(3, 'Transport', 'EXPENSE', NULL),
(4, 'Housing', 'EXPENSE', NULL),
(5, 'Entertainment', 'EXPENSE', NULL)
ON CONFLICT (id) DO NOTHING;
