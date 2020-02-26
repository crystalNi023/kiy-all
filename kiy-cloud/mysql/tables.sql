CREATE SCHEMA `kiy-cloud` DEFAULT CHARACTER SET utf8;
USE `kiy-cloud`;

CREATE TABLE `kiy-cloud`.`customers` (
  `id` CHAR(36) NOT NULL,
  `name` VARCHAR(64) NOT NULL COMMENT '公司名称',
  `code` VARCHAR(32) NULL COMMENT '营业执照统一代码',
  `phone` VARCHAR(32) NULL ,
  `id_card` VARCHAR(32) NULL,
  `province` VARCHAR(32) NULL,
  `district` VARCHAR(32) NULL,
  `city` VARCHAR(32) NULL,
  `town` VARCHAR(32) NULL,
  `address` VARCHAR(64) NULL,
  `remark` VARCHAR(64) NULL,
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`));
  
  CREATE TABLE `kiy-cloud`.`servos` (
  `id` CHAR(36) NOT NULL,
  `type` TINYINT(1) NOT NULL,
  `customer_id` CHAR(36) NOT NULL,
  `name` VARCHAR(32)  NOT NULL,
  `address` VARCHAR(32) NOT NULL,
  `remark` VARCHAR(64) NULL,
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`));
  
 CREATE TABLE `kiy-cloud`.`users` (
  `id` BIGINT(24) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` CHAR(36) NOT NULL ,
  `servo_id` CHAR(36) NOT NULL,
  `user_icon` VARCHAR(128) NOT NULL,
  `created` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
)  ENGINE=INNODB AUTO_INCREMENT=25567646040760880 DEFAULT CHARACTER SET=UTF8;
  
CREATE TABLE `kiy-cloud`.`feedback` (
  `id` BIGINT(24) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(32) NOT NULL,
  `servo_id` CHAR(36) NOT NULL,
  `content` VARCHAR(128) NOT NULL,
  `choice` INT(11) NULL,
  `img_url` VARCHAR(128) DEFAULT NULL,
  `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25567646040760883 DEFAULT CHARSET=utf8;


CREATE TABLE `kiy-cloud`.`single_product_user` (
  `id` CHAR(36) NOT NULL,
  `single_product_user_id` CHAR(36) NULL,
  `username` VARCHAR(32) NOT NULL,
  `password` VARCHAR(32) NOT NULL, 
  `xm_device_account` VARCHAR(32) NULL,
  `xm_device_password` VARCHAR(32) NULL,
  `enable` bit(1) NOT NULL DEFAULT b'1',
  `type` int(11) NOT NULL DEFAULT '0',
  `remark` VARCHAR(128)  NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=INNODB DEFAULT CHARACTER SET=UTF8;

CREATE TABLE `kiy-cloud`.`xm_device` (
  `id` CHAR(36) NOT NULL,
  `device_mac` VARCHAR(64) NOT NULL,
  `device_name` VARCHAR(64) NOT NULL,
  `login_name` VARCHAR(64) NULL,
  `login_psw` VARCHAR(64) NULL,
  `device_ip` VARCHAR(64) NULL,
  `state` int(11) NULL,
  `n_port` int(11) NULL,
  `n_type` int(11) NULL,
  `n_id` int(11) NULL,
  `remark` VARCHAR(128)  NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE INDEX `uq_xm_device_device_mac` (`device_mac` ASC),
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARACTER SET=UTF8;

CREATE TABLE `kiy-cloud`.`single_product_xm_device` (
	`single_product_user_id`  CHAR(36) NOT NULL,
	`xm_device_id`  CHAR(36) NOT NULL
);

ALTER TABLE `kiy-cloud`.`single_product_xm_device` 
ADD CONSTRAINT `fk_single_product_user_id`
  FOREIGN KEY (`single_product_user_id`)
  REFERENCES `single_product_user` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `kiy-cloud`.`single_product_xm_device` 
ADD CONSTRAINT `fk_xm_device_id`
  FOREIGN KEY (`xm_device_id`)
  REFERENCES `xm_device` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `kiy-cloud`.`servos` 
  ADD CONSTRAINT `fk_servos_customer_id`
  FOREIGN KEY (`customer_id`)
  REFERENCES `customers` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
  
ALTER TABLE `kiy-cloud`.`users` 
  ADD CONSTRAINT `fk_servo_id`
  FOREIGN KEY (`servo_id`)
  REFERENCES `servos` (`id`)
  ON DELETE NO ACTION 
  ON UPDATE NO ACTION;
  
ALTER TABLE `kiy-cloud`.`feedback` 
  ADD CONSTRAINT `fk_feedback_servo_id`
  FOREIGN KEY (`servo_id`)
  REFERENCES `servos` (`id`)
  ON DELETE NO ACTION 
  ON UPDATE NO ACTION;
  