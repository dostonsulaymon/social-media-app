-- First insert the admin profile with email as login
INSERT INTO profile (first_name, last_name, login, password, status, visible, created_date)
VALUES ('Admin',
        'User',
        'admin@admin.com',
        '$2a$10$bfG7/4tj7J/uJLPMzT58oebrYXZsWrRz.bqYuUhh5orDcWO2oQDXK',
        'ACTIVE',
        true,
        now());

-- Then insert the admin role for the created profile
INSERT INTO profile_role (profile_id, role, created_date)
VALUES (
           (SELECT id FROM profile WHERE login = 'admin@admin.com'),
           'ADMIN',
           now()
       );