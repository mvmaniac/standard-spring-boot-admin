INSERT INTO tb_role (role_id, role_name, role_description, created_by, updated_by, created_date, updated_date)
VALUES
(nextval('seq_tb_role'), 'ROLE_ADMIN', '관리자', 1, 1, '2020-05-20 23:06:14.947580', '2020-05-20 23:06:14.947580'),
(nextval('seq_tb_role'), 'ROLE_MANAGER', '매니저 권한', 1, 1, '2020-05-20 23:06:14.947580', '2020-05-20 23:06:14.947580'),
(nextval('seq_tb_role'), 'ROLE_USER', '사용자 권한', 1, 1, '2020-05-20 23:06:14.947580', '2020-05-20 23:06:14.947580')
;

INSERT INTO tb_account (account_id, account_username, account_password, account_email, account_age, created_by, updated_by, created_date, updated_date)
VALUES
(nextval('seq_tb_account'), 'admin', '{bcrypt}$2a$10$OuaOEaaap5GFL/zGB5asHum4rRzjs3H43JWs4kFPgILWpjlUwcoHC', 'admin@gmail.com', 30, 1, 1, '2020-05-20 23:06:14.947580', '2020-05-20 23:06:14.947580'),
(nextval('seq_tb_account'), 'dev1', '{bcrypt}$2a$10$OuaOEaaap5GFL/zGB5asHum4rRzjs3H43JWs4kFPgILWpjlUwcoHC', 'dev1@gmail.com', 30, 1, 1, '2020-05-20 23:06:14.947580', '2020-05-20 23:06:14.947580'),
(nextval('seq_tb_account'), 'manager', '{bcrypt}$2a$10$OuaOEaaap5GFL/zGB5asHum4rRzjs3H43JWs4kFPgILWpjlUwcoHC', 'manager@gmail.com', 30, 1, 1, '2020-05-20 23:06:14.947580', '2020-05-20 23:06:14.947580')
;

INSERT INTO tb_account_role (account_role_id, account_id, role_id, created_by, updated_by, created_date, updated_date)
VALUES
(nextval('seq_tb_account_role'), 1, 1, 1, 1, '2020-05-20 23:06:14.947580', '2020-05-20 23:06:14.947580'),
(nextval('seq_tb_account_role'), 2, 3, 1, 1, '2020-05-20 23:06:14.947580', '2020-05-20 23:06:14.947580'),
(nextval('seq_tb_account_role'), 3, 2, 1, 1, '2020-05-20 23:06:14.947580', '2020-05-20 23:06:14.947580')
;

INSERT INTO tb_resource (resource_id, resource_name, http_method, resource_type, order_no, role_id, created_by, updated_by, created_date, updated_date)
VALUES
(nextval('seq_tb_resource'), '/admin/**', '', 'url', 1, 1, 1, 1, '2020-05-20 23:06:14.947580', '2020-05-20 23:06:14.947580'),
(nextval('seq_tb_resource'), '/user/my', '', 'url', 2, 3, 1, 1, '2020-05-20 23:06:14.947580', '2020-05-20 23:06:14.947580'),
(nextval('seq_tb_resource'), '/user/message', '', 'url', 3, 2, 1, 1, '2020-05-20 23:06:14.947580', '2020-05-20 23:06:14.947580')
;
