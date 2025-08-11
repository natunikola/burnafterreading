CREATE TABLE passwords (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    password VARCHAR(255) NOT NULL,
    accessed BOOLEAN NOT NULL
);