INSERT INTO tb_role (role_id, role_name, role_description, created_by, updated_by, created_date, updated_date)
VALUES
(nextval('seq_tb_role'), 'ROLE_ADMIN', '관리자', 1, 1, '2020-05-20 23:06:14.947580', '2020-05-20 23:06:14.947580'),
(nextval('seq_tb_role'), 'ROLE_MANAGER', '매니저 권한', 1, 1, '2020-05-20 23:06:14.947580', '2020-05-20 23:06:14.947580'),
(nextval('seq_tb_role'), 'ROLE_USER', '사용자 권한', 1, 1, '2020-05-20 23:06:14.947580', '2020-05-20 23:06:14.947580')
;

INSERT INTO tb_role_hierarchy (role_hierarchy_id, child_id, parent_id, created_by, updated_by, created_date, updated_date)
VALUES
(nextval('seq_tb_role_hierarchy'), 1, null, 1, 1, '2020-05-20 23:06:14.947580', '2020-05-20 23:06:14.947580'),
(nextval('seq_tb_role_hierarchy'), 2, 1, 1, 1, '2020-05-20 23:06:14.947580', '2020-05-20 23:06:14.947580'),
(nextval('seq_tb_role_hierarchy'), 3, 2, 1, 1, '2020-05-20 23:06:14.947580', '2020-05-20 23:06:14.947580')
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
(nextval('seq_tb_resource'), '/user/message', '', 'url', 3, 2, 1, 1, '2020-05-20 23:06:14.947580', '2020-05-20 23:06:14.947580'),
(nextval('seq_tb_resource'), 'io.devfactory.account.service.AopService.methodSecured', '', 'method', 1, 3, 1, 1, '2020-05-20 23:06:14.947580', '2020-05-20 23:06:14.947580'),
(nextval('seq_tb_resource'), 'execution(* io.devfactory.account.service.AopService.pointcut*(..))', '', 'pointcut', 1, 3, 1, 1, '2020-05-20 23:06:14.947580', '2020-05-20 23:06:14.947580')
;

INSERT INTO tb_access_ip (access_ip_id, ip_address, created_by, updated_by, created_date, updated_date)
VALUES
(nextval('seq_tb_access_ip'), '127.0.0.1', 1, 1, '2020-05-20 23:06:14.947580', '2020-05-20 23:06:14.947580'),
(nextval('seq_tb_access_ip'), '0:0:0:0:0:0:0:1', 1, 1, '2020-05-20 23:06:14.947580', '2020-05-20 23:06:14.947580')
;
