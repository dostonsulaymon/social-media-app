-- First insert the admin profile
INSERT INTO profile (first_name, last_name, login, password, status, visible, created_date)
VALUES ('Admin',
        'User',
        'ADMIN',
        '$2a$10$bfG7/4tj7J/uJLPMzT58oebrYXZsWrRz.bqYuUhh5orDcWO2oQDXK',
        'ACTIVE',
        true,
        now());

-- Then insert the admin role for the created profile
INSERT INTO profile_role (profile_id, role, created_date)
SELECT id, 'ADMIN', now()
FROM profile
WHERE login = 'ADMIN';