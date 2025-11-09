-- MySQL Schema for BlogApp
-- This file can be used to create the required tables manually in MySQL
-- Useful when spring.jpa.hibernate.ddl-auto is set to 'validate' or 'none'

-- Create posts table
CREATE TABLE IF NOT EXISTS posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    author VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create index on author for faster lookups
CREATE INDEX idx_posts_author ON posts(author);

-- Create index on created_at for sorting
CREATE INDEX idx_posts_created_at ON posts(created_at);
