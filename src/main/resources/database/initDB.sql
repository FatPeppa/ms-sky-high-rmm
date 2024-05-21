/* Create Tables */

CREATE TABLE IF NOT EXISTS administrator_key_code
(
    id uuid NOT NULL,
    user_id uuid NULL,
    key_code_value varchar(32) NOT NULL,
    CONSTRAINT PK_administrator_key_code PRIMARY KEY (id)
)
;

CREATE TABLE IF NOT EXISTS block_reasons
(
    id varchar(10) NOT NULL,
    description text NOT NULL,
    CONSTRAINT PK_block_reasons PRIMARY KEY (id)
)
;

CREATE TABLE IF NOT EXISTS operation_permissions
(
    id uuid NOT NULL,
    permission_name text NOT NULL,
    operation_endpoint text NOT NULL,
    is_critical boolean NOT NULL,
    CONSTRAINT PK_operation_permissions PRIMARY KEY (id)
)
;

CREATE TABLE IF NOT EXISTS roles_operations
(
    id uuid NOT NULL,
    role_id uuid NOT NULL,
    permission_id uuid NOT NULL,
    CONSTRAINT PK_roles_operations PRIMARY KEY (id)
)
;

CREATE TABLE IF NOT EXISTS universal_user
(
    id uuid NOT NULL,
    login varchar(20) NOT NULL,
    password varchar(120) NOT NULL,
    user_info JSONB NULL,
    block_reason_id varchar(10) NULL,
    CONSTRAINT PK_UniversalUser PRIMARY KEY (id)
)
;

CREATE TABLE IF NOT EXISTS user_group_roles
(
    id uuid NOT NULL,
    role_name text NOT NULL,
    description text NULL,
    is_critical boolean NOT NULL,
    CONSTRAINT PK_user_group_roles PRIMARY KEY (id)
)
;

CREATE TABLE IF NOT EXISTS users_roles
(
    id uuid NOT NULL,
    user_id uuid NOT NULL,
    role_id uuid NOT NULL,
    CONSTRAINT PK_users_roles PRIMARY KEY (id)
)
;

/* Drop Foreign Key Constraints if they exist*/

ALTER TABLE administrator_key_code DROP CONSTRAINT IF EXISTS FK_administrator_key_code_universal_user;

ALTER TABLE roles_operations DROP CONSTRAINT IF EXISTS FK_roles_operations_operation_permissions;

ALTER TABLE roles_operations DROP CONSTRAINT IF EXISTS FK_roles_operations_user_group_roles;

ALTER TABLE universal_user DROP CONSTRAINT IF EXISTS FK_universal_user_block_reasons;

ALTER TABLE users_roles DROP CONSTRAINT IF EXISTS FK_users_roles_universal_user;

ALTER TABLE users_roles DROP CONSTRAINT IF EXISTS FK_users_roles_user_group_roles;

/* Create Foreign Key Constraints */

ALTER TABLE administrator_key_code ADD CONSTRAINT FK_administrator_key_code_universal_user
    FOREIGN KEY (user_id) REFERENCES universal_user (id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE roles_operations ADD CONSTRAINT FK_roles_operations_operation_permissions
    FOREIGN KEY (permission_id) REFERENCES operation_permissions (id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE roles_operations ADD CONSTRAINT FK_roles_operations_user_group_roles
    FOREIGN KEY (role_id) REFERENCES user_group_roles (id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE universal_user ADD CONSTRAINT FK_universal_user_block_reasons
    FOREIGN KEY (block_reason_id) REFERENCES block_reasons (id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE users_roles ADD CONSTRAINT FK_users_roles_universal_user
    FOREIGN KEY (user_id) REFERENCES universal_user (id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE users_roles ADD CONSTRAINT FK_users_roles_user_group_roles
    FOREIGN KEY (role_id) REFERENCES user_group_roles (id) ON DELETE No Action ON UPDATE No Action
;