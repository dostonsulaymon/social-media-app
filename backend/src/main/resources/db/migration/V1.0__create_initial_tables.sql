-- V1__Create_Initial_Tables.sql

-- Attaches table
CREATE TABLE attaches (
                          id VARCHAR(255) PRIMARY KEY,
                          path VARCHAR(255) NOT NULL,
                          extension VARCHAR(10),
                          origen_name VARCHAR(100),
                          size BIGINT,
                          created_date TIMESTAMP NOT NULL,
                          visible BOOLEAN DEFAULT true
);

-- Profile table
CREATE TABLE profile (
                         id BIGSERIAL PRIMARY KEY,
                         first_name VARCHAR(50),
                         last_name VARCHAR(50),
                         login VARCHAR(50) UNIQUE NOT NULL,
                         password VARCHAR(60) NOT NULL,
                         photo_id VARCHAR(255) REFERENCES attaches(id),
                         status VARCHAR(20) NOT NULL,
                         visible BOOLEAN DEFAULT true,
                         created_date TIMESTAMP NOT NULL,
                         updated_date TIMESTAMP
);

-- Profile Role table
CREATE TABLE profile_role (
                              id BIGSERIAL PRIMARY KEY,
                              profile_id BIGINT REFERENCES profile(id),
                              role VARCHAR(20) NOT NULL,
                              created_date TIMESTAMP NOT NULL
);

-- Posts table
CREATE TABLE posts (
                       id BIGSERIAL PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       content VARCHAR(255)
);

-- Post Attaches table
CREATE TABLE post_attaches (
                               id VARCHAR(255) PRIMARY KEY,
                               photo_id VARCHAR(255) REFERENCES attaches(id),
                               post_id BIGINT REFERENCES posts(id)
);

-- Comments table
CREATE TABLE comments (
                          id VARCHAR(255) PRIMARY KEY
);
