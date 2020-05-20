INSERT INTO tb_account (account_id, created_date, updated_date, created_by, updated_by, account_age, account_email, account_password, account_role, account_username)
VALUES (nextval('seq_tb_account'), '2020-05-20 23:06:14.947580', '2020-05-20 23:06:14.947580', null, null, 30, 'dev1@gmail.com', '{bcrypt}$2a$10$OuaOEaaap5GFL/zGB5asHum4rRzjs3H43JWs4kFPgILWpjlUwcoHC', 'ROLE_USER', 'dev1');
