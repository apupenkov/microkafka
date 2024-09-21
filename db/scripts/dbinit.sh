psql -U "$POSTGRES_USER" -d "$POSTGRES_DB"  <<EOSQL
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    amount VARCHAR(50) NOT NULL,
    user_id INT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE payments (
    id SERIAL PRIMARY KEY,
    sum VARCHAR(50) NOT NULL,
    order_id INT REFERENCES orders(id),
    user_id INT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users
(id, username, "password", created_at)
VALUES(nextval('users_id_seq'::regclass), '$TEST_USER', 'qwerty123', CURRENT_TIMESTAMP);

INSERT INTO orders
(id, amount, user_id, created_at)
VALUES(nextval('orders_id_seq'::regclass), '100.00',
(select u.id from users as u where u.username = '$TEST_USER'), CURRENT_TIMESTAMP);
EOSQL