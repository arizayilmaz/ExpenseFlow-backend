CREATE TABLE bank_accounts (
                               id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                               bank_name VARCHAR(255) NOT NULL,
                               iban VARCHAR(255) NOT NULL,
                               user_id UUID NOT NULL,
                               CONSTRAINT fk_bank_accounts_user FOREIGN KEY (user_id) REFERENCES users(id)
);