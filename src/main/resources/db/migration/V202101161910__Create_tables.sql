CREATE TABLE point_of_interest (
    id VARCHAR(60) NOT NULL,
    name VARCHAR(255) NOT NULL,
    pos_x INTEGER NOT NULL,
    pos_y INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_point_of_interest PRIMARY KEY (id)
);

CREATE INDEX idx_point_of_interest_name ON point_of_interest (name);
