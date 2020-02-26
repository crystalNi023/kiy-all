CREATE SCHEMA `kiy-travel-card` DEFAULT CHARACTER SET utf8;

USE `kiy-travel-card`;

CREATE TABLE `users` (
  `id` bigint(64) unsigned NOT NULL,
  `username` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `power` int(11) NOT NULL,
  `realname` varchar(45) NOT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `begin_number` int(11) DEFAULT NULL,
  `end_number` int(11) DEFAULT NULL,
  `remark` varchar(64) DEFAULT NULL,
  `enable` bit(1) NOT NULL DEFAULT b'0',
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `cards` (
  `id` bigint(64) unsigned NOT NULL,
  `user` bigint(64) unsigned NOT NULL,
  `number` bigint(32) NOT NULL,
  `type` int(11) NOT NULL,
  `remark` varchar(64) DEFAULT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_user_card_id` (`user`),
  CONSTRAINT `fk_user_card_id` FOREIGN KEY (`user`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `write_card_record` (
  `id` bigint(64) unsigned NOT NULL,
  `user` bigint(64) unsigned NOT NULL,
  `number` bigint(32) NOT NULL,
  `type` int(11) NOT NULL,
  `remark` varchar(64) DEFAULT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_wcr_user_idx` (`user`),
  CONSTRAINT `fk_wcr_user` FOREIGN KEY (`user`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `read_card_record` (
  `id` bigint(64) unsigned NOT NULL,
  `user` bigint(64) unsigned NOT NULL,
  `number` bigint(64) NOT NULL,
  `type` int(11) NOT NULL,
  `remark` varchar(64) DEFAULT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_rcr_user_idx` (`user`),
  CONSTRAINT `fk_rcr_user` FOREIGN KEY (`user`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `get_card_record` (
  `id` bigint(64) unsigned NOT NULL,
  `give_user` bigint(64) unsigned NOT NULL COMMENT '发卡人',
  `get_user` bigint(64) unsigned NOT NULL COMMENT '领卡人',
  `begin` int(11) NOT NULL,
  `end` int(11) NOT NULL,
  `count` int(11) NOT NULL,
  `remark` varchar(64) DEFAULT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_gcr_id_idx` (`give_user`),
  KEY `fk_gcr_get_id_idx` (`get_user`),
  CONSTRAINT `fk_gcr_get_id` FOREIGN KEY (`get_user`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_gcr_give_id` FOREIGN KEY (`give_user`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

