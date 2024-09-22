psql -U "$POSTGRES_USER" -d "$POSTGRES_DB"  <<EOSQL
create extension if not exists "uuid-ossp";
CREATE TABLE users (
    id UUID unique not null default uuid_generate_v1(),
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE orders (
    id UUID unique not null default uuid_generate_v1(),
    amount VARCHAR(50) NOT NULL,
    user_id UUID REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE payments (
    id UUID unique not null default uuid_generate_v1(),
    sum VARCHAR(50) NOT NULL,
    order_id UUID REFERENCES orders(id),
    user_id UUID REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users
(id, username, "password", created_at)
VALUES(uuid_generate_v1(), '$TEST_USER', 'qwerty123', CURRENT_TIMESTAMP);

INSERT INTO orders
(id, amount, user_id, created_at)
VALUES(uuid_generate_v1(), '100.00',
(select u.id from users as u where u.username = '$TEST_USER'), CURRENT_TIMESTAMP);
EOSQL