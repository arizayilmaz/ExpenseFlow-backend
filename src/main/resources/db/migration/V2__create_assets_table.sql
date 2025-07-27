CREATE TABLE assets (
                        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                        name VARCHAR(255) NOT NULL,
                        type VARCHAR(255) NOT NULL,
                        current_value DECIMAL(19, 2) NOT NULL,
                        user_id UUID NOT NULL,
                        CONSTRAINT fk_assets_user FOREIGN KEY (user_id) REFERENCES users(id)
);