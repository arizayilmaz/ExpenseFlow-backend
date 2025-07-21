
CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       account_non_expired BOOLEAN NOT NULL DEFAULT TRUE,
                       account_non_locked BOOLEAN NOT NULL DEFAULT TRUE,
                       credentials_non_expired BOOLEAN NOT NULL DEFAULT TRUE,
                       enabled BOOLEAN NOT NULL DEFAULT TRUE
);

-- Subscriptions Table
CREATE TABLE subscriptions (
                               id UUID PRIMARY KEY,
                               name VARCHAR(255) NOT NULL,
                               amount DECIMAL(19, 2) NOT NULL,
                               payment_day INT NOT NULL,
                               category VARCHAR(255) NOT NULL,
                               last_paid_cycle VARCHAR(255),
                               user_id UUID NOT NULL,
                               CONSTRAINT fk_subscriptions_user FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Expenses Table
CREATE TABLE expenses (
                          id UUID PRIMARY KEY,
                          description VARCHAR(255) NOT NULL,
                          amount DECIMAL(19, 2) NOT NULL,
                          category VARCHAR(255) NOT NULL,
                          date TIMESTAMP WITH TIME ZONE NOT NULL,
                          user_id UUID NOT NULL,
                          CONSTRAINT fk_expenses_user FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Investments Table
CREATE TABLE investments (
                             id UUID PRIMARY KEY,
                             type VARCHAR(255) NOT NULL,
                             name VARCHAR(255) NOT NULL,
                             amount DECIMAL(19, 4) NOT NULL, -- For crypto amounts with more precision
                             initial_value DECIMAL(19, 2) NOT NULL,
                             purchase_date TIMESTAMP WITH TIME ZONE NOT NULL,
                             api_id VARCHAR(255),
                             user_id UUID NOT NULL,
                             CONSTRAINT fk_investments_user FOREIGN KEY (user_id) REFERENCES users(id)
);