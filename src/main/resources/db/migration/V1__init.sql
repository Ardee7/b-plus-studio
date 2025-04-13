-- USERS TABLE
CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    username VARCHAR(100) NOT NULL,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    password_hash VARCHAR(255), -- Nullable for SSO users
    oauth_provider VARCHAR(50), -- e.g., GOOGLE, FACEBOOK
    oauth_id VARCHAR(255) UNIQUE, -- Nullable for email/password users
    role VARCHAR(50) DEFAULT 'USER',
    profile_picture VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    last_login TIMESTAMP,
    status VARCHAR(50) DEFAULT 'ACTIVE' NOT NULL
);

--users insert scripts - START
INSERT INTO users (
    id,
    email,
    username,
    firstname,
    lastname,
    password_hash,
    oauth_provider,
    oauth_id,
    role,
    profile_picture,
    created_at,
    updated_at,
    last_login,
    status
)
VALUES (
    '362000f6-f226-43c6-8ab1-2b2343762447',
    'admin@bplus-studio',
    'admin@bplus-studio',
    'Admin',
    'BplusStudio',
    '$2a$12$yepIAklRFuHH8GDHfl0F7eamYlO6IQ.JHK2/nIGznLYsfC24XW.B6', -- bcrypt hash for '@wsxzaq!admin'
    NULL,
    NULL,
    'SUPER_ADMIN',
    NULL,
    CURRENT_TIMESTAMP,
    NULL,
    NULL,
    'ACTIVE'
);

--users insert scripts - END

-- CATEGORIES TABLE
CREATE TABLE IF NOT EXISTS categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- BUSINESSES TABLE
CREATE TABLE IF NOT EXISTS businesses (
    id BIGSERIAL PRIMARY KEY,
    owner_id UUID NOT NULL,
    name VARCHAR(255) UNIQUE NOT NULL,
    description TEXT,
    category_id BIGINT NOT NULL,
    logo VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    status VARCHAR(50) DEFAULT 'ACTIVE' NOT NULL,
    CONSTRAINT fk_business_owner FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_business_category FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);

-- PRODUCTS TABLE
CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY,
    business_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock INT DEFAULT 0 NOT NULL,
    status VARCHAR(50) DEFAULT 'IN_STOCK' NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_product_business FOREIGN KEY (business_id) REFERENCES businesses(id) ON DELETE CASCADE
);

-- ORDERS TABLE
CREATE TABLE IF NOT EXISTS orders (
    id BIGSERIAL PRIMARY KEY,
    user_id UUID NOT NULL,
    business_id BIGINT NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    status VARCHAR(50) DEFAULT 'PENDING' NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_order_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_order_business FOREIGN KEY (business_id) REFERENCES businesses(id) ON DELETE CASCADE
);

-- ORDER_ITEMS TABLE
CREATE TABLE IF NOT EXISTS order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT DEFAULT 1 NOT NULL,
    price_at_purchase DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_order_item_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    CONSTRAINT fk_order_item_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- REVIEWS TABLE
CREATE TABLE IF NOT EXISTS reviews (
    id BIGSERIAL PRIMARY KEY,
    user_id UUID NOT NULL,
    business_id BIGINT, -- Nullable (for product reviews)
    product_id BIGINT,  -- Nullable (for business reviews)
    rating INT CHECK (rating BETWEEN 1 AND 5) NOT NULL,
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT fk_review_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_review_business FOREIGN KEY (business_id) REFERENCES businesses(id) ON DELETE CASCADE,
    CONSTRAINT fk_review_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- FAVORITES TABLE
CREATE TABLE IF NOT EXISTS favorites (
    id BIGSERIAL PRIMARY KEY,
    user_id UUID NOT NULL,
    business_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT fk_favorite_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_favorite_business FOREIGN KEY (business_id) REFERENCES businesses(id) ON DELETE CASCADE,
    UNIQUE (user_id, business_id) -- Prevent duplicate favorites
);

-- INDEXES FOR PERFORMANCE (Check existence before creating)
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_users_email') THEN
        CREATE INDEX idx_users_email ON users(email);
    END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_businesses_owner') THEN
        CREATE INDEX idx_businesses_owner ON businesses(owner_id);
    END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_orders_user') THEN
        CREATE INDEX idx_orders_user ON orders(user_id);
    END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_products_business') THEN
        CREATE INDEX idx_products_business ON products(business_id);
    END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_reviews_user') THEN
        CREATE INDEX idx_reviews_user ON reviews(user_id);
    END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_favorites_user') THEN
        CREATE INDEX idx_favorites_user ON favorites(user_id);
    END IF;
END $$;
