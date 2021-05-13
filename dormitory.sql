-- 用户信息
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id`           INT(11)     NOT NULL AUTO_INCREMENT,
  `username`     VARCHAR(50) NOT NULL,
  `role`         VARCHAR(10) NOT NULL,
  `password`     VARCHAR(50) NOT NULL,
  `phone_number` VARCHAR(20)  DEFAULT NULL,
  `create_time`  TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`),
  UNIQUE KEY (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = `utf8mb4`
  COLLATE `utf8mb4_bin`;

-- 订单信息
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id`              INT(11) NOT NULL AUTO_INCREMENT,
  `state`           VARCHAR(20)  DEFAULT 'ORDERING',
  `order_user`      INT(11) NOT NULL,
  `order_time`      TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6),
  `service_person`  VARCHAR(100) DEFAULT NULL,
  `price`           INT(11)      DEFAULT NULL,
  `finished_time`   TIMESTAMP(6) DEFAULT NULL,
  `evaluation`      TEXT         DEFAULT NULL,
  `evaluation_star` INT(11)      DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = `utf8mb4`
  COLLATE `utf8mb4_bin`;

-- 通知信息
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id`          INT(11) NOT NULL AUTO_INCREMENT,
  `state`       VARCHAR(20)  DEFAULT 'UNREAD',
  `target_user` INT(11) NOT NULL,
  `content`     TEXT    NOT NULL NULL,
  `create_time` TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = `utf8mb4`
  COLLATE `utf8mb4_bin`;

INSERT INTO `user`(`username`, `role`, `password`, `phone_number`)
VALUES ('august', 'ADMIN', '1234', '13100020002');