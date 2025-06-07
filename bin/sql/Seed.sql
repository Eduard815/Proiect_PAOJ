CREATE TABLE clients (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100),
    city VARCHAR(100),
    street VARCHAR(100),
    number INT,
    postal_code VARCHAR(20)
);

CREATE TABLE accounts (
    id VARCHAR(50) PRIMARY KEY,
    iban VARCHAR(50) UNIQUE,
    balance DOUBLE PRECISION,
    opened_date DATE,
    type VARCHAR(20),
    client_id VARCHAR(50),
    overdraft_limit DOUBLE PRECISION,
    min_balance DOUBLE PRECISION,
    interest_rate DOUBLE PRECISION,
    is_blocked BOOLEAN,
    FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE
);

CREATE TABLE cards (
    card_number VARCHAR(50) PRIMARY KEY,
    expiration_date DATE,
    ccv INT,
    owner VARCHAR(100),
    active BOOLEAN,
    type VARCHAR(20),
    account_id VARCHAR(50),
    credit_limit DOUBLE PRECISION,
    interest_rate DOUBLE PRECISION,
    FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE
);

CREATE TABLE transactions (
    id VARCHAR(50) PRIMARY KEY,
    description TEXT,
    amount DOUBLE PRECISION,
    type VARCHAR(20),
    date DATE,
    account_id VARCHAR(50),
    FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE
);

CREATE TABLE notifications (
    id VARCHAR(50) PRIMARY KEY,
    message TEXT,
    date DATE,
    read BOOLEAN DEFAULT FALSE,
    account_id VARCHAR(50),
    FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE
);