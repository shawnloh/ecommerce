CREATE TABLE Category
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT unique_category_name UNIQUE (name)
);