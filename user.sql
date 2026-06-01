CREATE DATABASE `kb7-jdbc`;
USE `kb7-jdbc`;

CREATE TABLE `user` (
	id 				INT 			AUTO_INCREMENT PRIMARY KEY,
    user_id         VARCHAR(50)     NOT NULL UNIQUE,
    name            VARCHAR(20)     NOT NULL,
    password        VARCHAR(50)     NOT NULL,    
    create_at       DATETIME        DEFAULT CURRENT_TIMESTAMP    
);

CREATE TABLE `address` (
	id 				INT 			AUTO_INCREMENT PRIMARY KEY,
    
)

CREATE TABLE `cake`  (
	id 				INT 			AUTO_INCREMENT PRIMARY KEY,
	preference      VARCHAR(20),
    user_id_fk    INT,
    
	CONSTRAINT fk_cake_user FOREIGN KEY (user_id_fk) REFERENCES `user` (id)
);

INSERT INTO `user` (user_id, name, password)
VALUES ('lhs', '이효석', '1324');

INSERT INTO `user` (user_id, name, password)
VALUES ('sj', '순자', 'abcd');

SELECT * FROM `user`;